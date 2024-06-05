package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.*;
import com.enexse.intranet.ms.accounting.models.partials.EesUser;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.*;
import com.enexse.intranet.ms.accounting.request.EesUserTimeSheetRequest;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import com.enexse.intranet.ms.accounting.utils.EesCommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EesUserTimesheetService {

    private EesTimesheetWorkPlaceRepository workplaceRepository;
    private EesTimeSheetWorkTimeRepository worktimeRepository;
    private EesTimesheetActivityRepository activityRepository;
    private EesUserService userService;
    private EesTimesheetContractHourRepository contractHoursRepository;
    private EesUserTimeSheetRepository userTimeSheetRepository;
    private EesSummaryTimesheetRepository eesSummaryTimesheetRepository;
    private EesTimesheetProjectRepository projectRepository;

    @Transactional
    public ResponseEntity<Object> updateUser(String userId, EesUserTimeSheetRequest request) {
        EesUser user = userService.findById(userId);

        if (user != null) {
            Optional<EesTimeSheetActivity> activity = activityRepository.findById(request.getActivityId());
            Optional<EesTimesheetWorkplace> workplace = workplaceRepository.findById(request.getWorkplaceId());
            Optional<EesTimeSheetWorkTime> workTime = worktimeRepository.findById(request.getWorktimeId());
            Optional<EesTimesheetContractHour> contractHoursClient = contractHoursRepository.findById(request.getContractHoursClientId());
            Optional<EesTimesheetContractHour> contractHoursEnexse = contractHoursRepository.findById(request.getContractHoursEnexseId());
            Optional<EesUserTimeSheet> userTimeSheetOptional = userTimeSheetRepository.findByUserId(userId);
            EesUserTimeSheet userTimeSheet;
            if (userTimeSheetOptional.isPresent()) {
                userTimeSheet = userTimeSheetOptional.get();
            } else {
                userTimeSheet = new EesUserTimeSheet();
                userTimeSheet.setUserId(userId);
            }
            userTimeSheet.setRtt(request.getRtt());
            userTimeSheet.setUserId(userId);
            userTimeSheet.setBusinessManagerType(request.getBusinessManagerType());
            userTimeSheet.setBusinessManagerEnexse(request.getBusinessManagerEnexse());
            userTimeSheet.setBusinessManagerClient(request.getBusinessManagerClient());
            userTimeSheet.setNumAffair(request.getNumAffair());
            userTimeSheet.setNumOrder(request.getNumOrder());
            userTimeSheet.setCustomerId(request.getCustomerId());
            userTimeSheet.setTimeSheetActivity(activity.get());
            userTimeSheet.setWorkplace(workplace.get());
            userTimeSheet.setWorktime(workTime.get());
            userTimeSheet.setContractHoursClient(contractHoursClient.get());
            userTimeSheet.setContractHoursEnexse(contractHoursEnexse.get());
            //userTimeSheet.setProjectName(request.getProjectName());

            // Save projects information
            List<EesTimesheetProject> projects = projectRepository.findAllByUserId(userId);
            if (projects.size() == 0) {
                List<EesTimesheetProject> newProjects = new ArrayList<EesTimesheetProject>();
                request.getProjects().stream().forEach(project ->
                        newProjects.add(new EesTimesheetProject()
                                .builder()
                                .userId(userId)
                                .careerId(project)
                                .build()));
                projectRepository.saveAll(newProjects);
            } else {
                // update project
                List<EesTimesheetProject> projectToDelete = projects.stream().filter(project -> !request.getProjects().contains(project.getCareerId())).collect(Collectors.toList());
                projectRepository.deleteAll(projectToDelete);
                List<EesTimesheetProject> projectToUpdate = projects.stream().filter(project -> request.getProjects().contains(project.getCareerId())).collect(Collectors.toList());
                projectRepository.saveAll(projectToUpdate);
            }

            // Save user timesheet information
            userTimeSheetRepository.save(userTimeSheet);

            // Also update some fields related to summary
            Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(userId, EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
            if (summary.isPresent()) {
                summary.get().setContractHoursClientPerWeek(contractHoursClient.get().getContractHoursId());
                eesSummaryTimesheetRepository.save(summary.get());
            }
        } else {
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_USER_NOT_FOUND)), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    public EesUserTimeSheet getUserTimesheet(String userId) {
        Optional<EesUserTimeSheet> userTimeSheetOptional = userTimeSheetRepository.findByUserId(userId);
        EesUserTimeSheet userTimeSheet = null;
        if (userTimeSheetOptional.isPresent()) {
            userTimeSheet = userTimeSheetOptional.get();
            userTimeSheet.setTimeSheetProjects(projectRepository.findAllByUserId(userId));
        }
        return userTimeSheet;
    }
}

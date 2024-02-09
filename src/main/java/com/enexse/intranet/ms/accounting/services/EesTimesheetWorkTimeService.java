package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.EesTimeSheetWorkTime;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.EesTimeSheetWorkTimeRepository;
import com.enexse.intranet.ms.accounting.request.EesTimeSheetWorkTimeRequest;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import com.enexse.intranet.ms.accounting.response.EesTimeSheetWorkTimeResponse;
import com.enexse.intranet.ms.accounting.utils.EesCommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EesTimesheetWorkTimeService {
    private EesTimeSheetWorkTimeRepository eesTimeSheetWorkTimeRepository;
    private EesUserService userService;

    public ResponseEntity<Object> insertTimeSheetWorkTime(EesTimeSheetWorkTimeRequest request) {
        String worktimeCode = request.getWorktimeCode();
        if (worktimeCode == null || worktimeCode.trim().isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_CODE_COULD_NOT_BE_NULL), HttpStatus.BAD_REQUEST);
        }

        if (!worktimeCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_PREFIX)) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_CODE_INVALID_FORMAT), HttpStatus.BAD_REQUEST);
        }

        if ((eesTimeSheetWorkTimeRepository.findByCode(worktimeCode)).isPresent()) {
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_ALREADY_EXISTS, request.getWorktimeCode())), HttpStatus.BAD_REQUEST);
        }

        EesTimeSheetWorkTime worktime = EesTimeSheetWorkTime
                .builder()
                .worktimeCode(request.getWorktimeCode())
                .worktimeTitle(request.getWorktimeTitle())
                .worktimeDescription(request.getWorktimeDescription())
                .createdAt(EesCommonUtil.generateCurrentDateUtil())
                .updatedAt(EesCommonUtil.generateCurrentDateUtil())
                .userId(request.getUserId())
                .build();

        eesTimeSheetWorkTimeRepository.save(worktime);
        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_CREATED), HttpStatus.CREATED);
    }

    public List<EesTimeSheetWorkTimeResponse> getAllTimeSheetWorktimes() {
        List<EesTimeSheetWorkTime> workTimes = eesTimeSheetWorkTimeRepository.findAll();
        List<EesTimeSheetWorkTimeResponse> workTimesResponse = new ArrayList<EesTimeSheetWorkTimeResponse>();
        workTimes.stream().map(workTime -> {
            EesTimeSheetWorkTimeResponse response = new EesTimeSheetWorkTimeResponse()
                            .builder()
                            .worktimeId(workTime.getWorktimeId())
                            .worktimeCode(workTime.getWorktimeCode())
                            .worktimeDescription(workTime.getWorktimeDescription())
                            .worktimeTitle(workTime.getWorktimeTitle())
                            .createdAt(workTime.getCreatedAt())
                            .updatedAt(workTime.getUpdatedAt())
                            .createdBy(userService.findById(workTime.getUserId()))
                            .build();
                    return workTimesResponse.add(response);
                }
        ).collect(Collectors.toList());
        return workTimesResponse;
    }

    public ResponseEntity<Object> updateTimeSheetWorkTimeByCode(String worktimeCode, EesTimeSheetWorkTimeRequest request) {
        if (!worktimeCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_PREFIX)) {
            worktimeCode = EesTimesheetResponse.EES_TIMESHEET_WORKTIME_PREFIX + worktimeCode;
        }
        Optional<EesTimeSheetWorkTime> worktime = eesTimeSheetWorkTimeRepository.findByCode(worktimeCode);
        if (worktime.isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_NOT_FOUND + "" + worktimeCode), HttpStatus.NOT_FOUND);
        } else {
            if (!request.getWorktimeCode().equals(worktimeCode)) {
                Optional<EesTimeSheetWorkTime> existingWorkTime = eesTimeSheetWorkTimeRepository.findByCode(request.getWorktimeCode().toUpperCase(Locale.ROOT));
                if (existingWorkTime.isPresent()) {
                    return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_ALREADY_EXISTS, request.getWorktimeCode())), HttpStatus.BAD_REQUEST);
                }
            }
        }
        worktime.get().setWorktimeCode(request.getWorktimeCode());
        worktime.get().setWorktimeTitle(request.getWorktimeTitle());
        worktime.get().setWorktimeDescription(request.getWorktimeDescription());
        worktime.get().setUserId(request.getUserId());
        worktime.get().setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());
        eesTimeSheetWorkTimeRepository.save(worktime.get());
        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_UPDATE_BY_CODE_TIMESHEET_WORKTIME), HttpStatus.OK);
    }

    public Optional<EesTimeSheetWorkTime> getTimeSheetWorktimeByCode(String workTimeCode) {
        if (!workTimeCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_PREFIX)) {
            workTimeCode = EesTimesheetResponse.EES_TIMESHEET_WORKTIME_PREFIX + workTimeCode;
        }
        if ((eesTimeSheetWorkTimeRepository.findByCode(workTimeCode)).isEmpty()) {
            return null;
        } else {
            return eesTimeSheetWorkTimeRepository.findByCode(workTimeCode);
        }
    }

    public ResponseEntity<Object> deleteTimeSheetWorkTimeByCode(String workTimeCode) {
        Optional<EesTimeSheetWorkTime> workTime = eesTimeSheetWorkTimeRepository.findByCode(workTimeCode);
        if (workTime == null) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_NOT_FOUND), HttpStatus.NOT_FOUND);
        } else {
            eesTimeSheetWorkTimeRepository.delete(workTime.get());
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKTIME_DELETED), HttpStatus.OK);
        }
    }
}

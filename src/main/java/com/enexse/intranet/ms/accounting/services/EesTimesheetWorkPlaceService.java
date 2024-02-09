package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.EesTimesheetWorkplace;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.EesTimesheetWorkPlaceRepository;
import com.enexse.intranet.ms.accounting.request.EesTimesheetWorkPlaceRequest;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import com.enexse.intranet.ms.accounting.response.EesTimeSheetWorkPlaceResponse;
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
public class EesTimesheetWorkPlaceService {

    private EesTimesheetWorkPlaceRepository workPlaceRepository;
    private EesUserService userService;

    public ResponseEntity<Object> insertTimeSheetWorkPlace(EesTimesheetWorkPlaceRequest workPlaceRequest) {
        String workPlaceCode = workPlaceRequest.getWorkPlaceCode();

        if (workPlaceCode == null || workPlaceCode.trim().isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_CODE_COULD_NOT_BE_NULL), HttpStatus.BAD_REQUEST);
        }

        if (!workPlaceCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_PREFIX)) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_CODE_INVALID_FORMAT), HttpStatus.BAD_REQUEST);
        }

        if ((workPlaceRepository.findByCode(workPlaceCode)).isPresent()) {
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_ALREADY_EXISTS, workPlaceRequest.getWorkPlaceCode())), HttpStatus.BAD_REQUEST);
        }

        EesTimesheetWorkplace workplace = EesTimesheetWorkplace
                .builder()
                .workPlaceCode(workPlaceRequest.getWorkPlaceCode())
                .workPlaceDesignation(workPlaceRequest.getWorkPlaceDesignation())
                .workPlaceObservation(workPlaceRequest.getWorkPlaceObservation())
                .createdAt(EesCommonUtil.generateCurrentDateUtil())
                .updatedAt(EesCommonUtil.generateCurrentDateUtil())
                .userId(workPlaceRequest.getUserId())
                .build();
        workPlaceRepository.save(workplace);
        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_CREATED), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> updateTimeSheetWorkPlaceByCode(String workPlaceCode, EesTimesheetWorkPlaceRequest workPlaceRequest) {
        if (!workPlaceCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_PREFIX)) {
            workPlaceCode = EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_PREFIX + workPlaceCode;
        }
        Optional<EesTimesheetWorkplace> workplace = workPlaceRepository.findByCode(workPlaceCode);
        System.out.println("++++" + workplace);
        if (workplace.isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_NOT_FOUND + "" + workPlaceCode), HttpStatus.NOT_FOUND);
        } else {
            if (!workPlaceRequest.getWorkPlaceCode().equals(workPlaceCode)) {
                Optional<EesTimesheetWorkplace> existingWorkPlace = workPlaceRepository.findByCode(workPlaceRequest.getWorkPlaceCode().toUpperCase(Locale.ROOT));
                if (existingWorkPlace.isPresent()) {
                    return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_ALREADY_EXISTS, workPlaceRequest.getWorkPlaceCode())), HttpStatus.BAD_REQUEST);
                }
            }
        }
        workplace.get().setWorkPlaceCode(workPlaceRequest.getWorkPlaceCode());
        workplace.get().setWorkPlaceObservation(workPlaceRequest.getWorkPlaceObservation());
        workplace.get().setWorkPlaceDesignation(workPlaceRequest.getWorkPlaceDesignation());
        workplace.get().setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());
        workplace.get().setUserId(workPlaceRequest.getUserId());
        workPlaceRepository.save(workplace.get());
        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_UPDATE_BY_CODE_TIMESHEET_WORKPLACE), HttpStatus.OK);
    }

    public List<EesTimeSheetWorkPlaceResponse> getAllTimeSheetWorkPlaces() {
        List<EesTimesheetWorkplace> workplaces = workPlaceRepository.findAll();
        List<EesTimeSheetWorkPlaceResponse> workplacesResponse = new ArrayList<EesTimeSheetWorkPlaceResponse>();
        workplaces.stream().map(workplace -> {
            EesTimeSheetWorkPlaceResponse response = new EesTimeSheetWorkPlaceResponse()
                            .builder()
                            .workPlaceId(workplace.getWorkPlaceId())
                            .workPlaceCode(workplace.getWorkPlaceCode())
                            .workPlaceObservation(workplace.getWorkPlaceObservation())
                            .workPlaceDesignation(workplace.getWorkPlaceDesignation())
                            .createdAt(workplace.getCreatedAt())
                            .updatedAt(workplace.getUpdatedAt())
                            .createdBy(userService.findById(workplace.getUserId()))
                            .build();
                    return workplacesResponse.add(response);
                }
        ).collect(Collectors.toList());
        return workplacesResponse;
    }

    public Optional<EesTimesheetWorkplace> getTimeSheetWorkPlaceByCode(String workPlaceCode) {
        if (!workPlaceCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_PREFIX)) {
            workPlaceCode = EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_PREFIX + workPlaceCode;
        }
        if ((workPlaceRepository.findByCode(workPlaceCode)).isEmpty()) {
            return null;
        } else {
            return workPlaceRepository.findByCode(workPlaceCode);
        }
    }

    public ResponseEntity<Object> deleteTimeSheetWorkPlaceByCode(String workPlaceCode) {
        Optional<EesTimesheetWorkplace> workplace = workPlaceRepository.findByCode(workPlaceCode);
        if (workplace == null) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_NOT_FOUND), HttpStatus.NOT_FOUND);
        } else {
            workPlaceRepository.delete(workplace.get());
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_WORKPLACE_DELETED), HttpStatus.OK);
        }
    }

    public String getTitleByCode(String workplaceCode) {

        Optional<EesTimesheetWorkplace> workplace = workPlaceRepository.findByCode(workplaceCode);

        if (workplace != null) {
            return workplace.get().getWorkPlaceDesignation();
        } else {
            return null;
        }
    }

}

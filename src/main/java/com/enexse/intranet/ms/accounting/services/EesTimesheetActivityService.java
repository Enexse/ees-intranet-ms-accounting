package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.EesTimeSheetActivity;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.EesSummaryTimesheetRepository;
import com.enexse.intranet.ms.accounting.repositories.EesTimesheetActivityRepository;
import com.enexse.intranet.ms.accounting.request.EesTimesheetActivityRequest;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import com.enexse.intranet.ms.accounting.response.EesTimeSheetActivityResponse;
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
public class EesTimesheetActivityService {

    private EesTimesheetActivityRepository activityRepository;

    private EesSummaryTimesheetRepository eesSummaryTimesheetRepository;
    private EesUserService userService;

    public ResponseEntity<Object> insertTimeSheetActivity(EesTimesheetActivityRequest request) {
        String activityCode = request.getActivityCode();

        if (activityCode == null || activityCode.trim().isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_CODE_COULD_NOT_BE_NULL), HttpStatus.BAD_REQUEST);
        }

        if (!activityCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_PREFIX)) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_CODE_INVALID_FORMAT), HttpStatus.BAD_REQUEST);
        }

        if ((activityRepository.findByCode(activityCode)).isPresent()) {
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_ALREADY_EXISTS, request.getActivityCode())), HttpStatus.BAD_REQUEST);
        }

        EesTimeSheetActivity activity = EesTimeSheetActivity
                .builder()
                .activityCode(request.getActivityCode())
                .activityDesignation(request.getActivityDesignation())
                .activityObservation(request.getActivityObservation())
                .createdAt(EesCommonUtil.generateCurrentDateUtil())
                .updatedAt(EesCommonUtil.generateCurrentDateUtil())
                .userId(request.getUserId())
                .build();
        activityRepository.save(activity);
        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_CREATED), HttpStatus.CREATED);
    }

    public List<EesTimeSheetActivityResponse> getAllTimeSheetActivities() {
        List<EesTimeSheetActivity> activities = activityRepository.findAll();
        List<EesTimeSheetActivityResponse> activitiesResponse = new ArrayList<EesTimeSheetActivityResponse>();
        activities.stream().map(activity -> {
                    EesTimeSheetActivityResponse response = new EesTimeSheetActivityResponse()
                            .builder()
                            .activityId(activity.getActivityId())
                            .activityCode(activity.getActivityCode())
                            .activityDesignation(activity.getActivityDesignation())
                            .activityObservation(activity.getActivityObservation())
                            .createdAt(activity.getCreatedAt())
                            .updatedAt(activity.getUpdatedAt())
                            .createdBy(userService.findById(activity.getUserId()))
                            .build();
                    return activitiesResponse.add(response);
                }
        ).collect(Collectors.toList());
        return activitiesResponse;
    }

    public ResponseEntity<Object> updateTimeSheetActivityByCode(String activityCode, EesTimesheetActivityRequest request) {
        if (!activityCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_PREFIX)) {
            activityCode = EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_PREFIX + activityCode;
        }
        Optional<EesTimeSheetActivity> activity = activityRepository.findByCode(activityCode);
        if (activity.isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_NOT_FOUND + "" + activityCode), HttpStatus.NOT_FOUND);
        } else {
            if (!request.getActivityCode().equals(activityCode)) {
                Optional<EesTimeSheetActivity> existingActivity = activityRepository.findByCode(request.getActivityCode().toUpperCase(Locale.ROOT));
                if (existingActivity.isPresent()) {
                    return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_ALREADY_EXISTS, request.getActivityCode())), HttpStatus.BAD_REQUEST);
                }
            }
        }
        activity.get().setActivityCode(request.getActivityCode());
        activity.get().setActivityObservation(request.getActivityObservation());
        activity.get().setActivityDesignation(request.getActivityDesignation());
        activity.get().setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());
        activity.get().setUserId(request.getUserId());
        activityRepository.save(activity.get());
        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_UPDATE_BY_CODE_TIMESHEET_ACTIVITY), HttpStatus.OK);
    }

    public Optional<EesTimeSheetActivity> getTimeSheetActivityByCode(String activityCode) {
        if (!activityCode.startsWith(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_PREFIX)) {
            activityCode = EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_PREFIX + activityCode;
        }
        if ((activityRepository.findByCode(activityCode)).isEmpty()) {
            return null;
        } else {
            return activityRepository.findByCode(activityCode);
        }
    }

    public ResponseEntity<Object> deleteTimeSheetActivityByCode(String activityCode) {
        Optional<EesTimeSheetActivity> activity = activityRepository.findByCode(activityCode);
        if (activity.isEmpty()) {
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_NOT_FOUND), HttpStatus.NOT_FOUND);
        } else {
            activityRepository.delete(activity.get());
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_ACTIVITY_DELETED), HttpStatus.OK);
        }
    }
}

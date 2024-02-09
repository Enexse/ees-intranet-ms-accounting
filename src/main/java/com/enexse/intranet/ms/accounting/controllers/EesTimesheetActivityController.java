package com.enexse.intranet.ms.accounting.controllers;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetConstant;
import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.EesTimeSheetActivity;
import com.enexse.intranet.ms.accounting.request.EesTimesheetActivityRequest;
import com.enexse.intranet.ms.accounting.response.EesTimeSheetActivityResponse;
import com.enexse.intranet.ms.accounting.services.EesTimesheetActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EesTimesheetEndpoint.EES_ROOT_ENDPOINT)
@CrossOrigin("*")
@AllArgsConstructor
public class EesTimesheetActivityController {

    private EesTimesheetActivityService timeSheetActivityService;

    @PostMapping(EesTimesheetEndpoint.EES_INSERT_TIMESHEET_ACTIVITY)
    public ResponseEntity<Object> eesInsertTimeSheetActivity(@RequestBody EesTimesheetActivityRequest request) {
        return timeSheetActivityService.insertTimeSheetActivity(request);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_All_TIMESHEET_ACTIVITIES)
    public List<EesTimeSheetActivityResponse> eesGetAllTimeSheetActivities() {
        return timeSheetActivityService.getAllTimeSheetActivities();
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @PutMapping(EesTimesheetEndpoint.EES_UPDATE_BY_CODE_TIMESHEET_ACTIVITY)
    public ResponseEntity<Object> eesUpdateTimeSheetActivityByCode(@PathVariable String activityCode, @RequestBody EesTimesheetActivityRequest request) {
        return timeSheetActivityService.updateTimeSheetActivityByCode(activityCode, request);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_GET_TIMESHEET_ACTIVITY_BY_CODE)
    public Optional<EesTimeSheetActivity> eesGetTimeSheetActivityByCode(@RequestParam String activityCode) {
        return timeSheetActivityService.getTimeSheetActivityByCode(activityCode);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @DeleteMapping(EesTimesheetEndpoint.EES_DELETE_BY_CODE_TIMESHEET_ACTIVITY)
    public ResponseEntity<Object> eesDeleteTimeSheetActivityByCode(@PathVariable String activityCode) {
        return timeSheetActivityService.deleteTimeSheetActivityByCode(activityCode);
    }
}

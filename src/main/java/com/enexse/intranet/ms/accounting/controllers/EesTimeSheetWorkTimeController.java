package com.enexse.intranet.ms.accounting.controllers;


import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.EesTimeSheetWorkTime;
import com.enexse.intranet.ms.accounting.request.EesTimeSheetWorkTimeRequest;
import com.enexse.intranet.ms.accounting.response.EesTimeSheetWorkTimeResponse;
import com.enexse.intranet.ms.accounting.services.EesTimesheetWorkTimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EesTimesheetEndpoint.EES_ROOT_ENDPOINT)
@CrossOrigin("*")
@AllArgsConstructor
public class EesTimeSheetWorkTimeController {
    private EesTimesheetWorkTimeService eesTimesheetWorkTimeService;

    @PostMapping(EesTimesheetEndpoint.EES_INSERT_TIMESHEET_WORKTIME)
    public ResponseEntity<Object> eesInsertTimeSheetWorktime(@RequestBody EesTimeSheetWorkTimeRequest request) {
        return eesTimesheetWorkTimeService.insertTimeSheetWorkTime(request);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_All_TIMESHEET_WORKTIMS)
    public List<EesTimeSheetWorkTimeResponse> eesGetAllTimeSheetWorkTimes() {
        return eesTimesheetWorkTimeService.getAllTimeSheetWorktimes();
    }

    @PutMapping(EesTimesheetEndpoint.EES_UPDATE_BY_CODE_TIMESHEET_WORKTIME)
    public ResponseEntity<Object> eesUpdateTimeSheetWorktimeByCode(@PathVariable String worktimeCode, @RequestBody EesTimeSheetWorkTimeRequest request) {
        return eesTimesheetWorkTimeService.updateTimeSheetWorkTimeByCode(worktimeCode, request);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_TIMESHEET_WORKTIME_BY_CODE)
    public Optional<EesTimeSheetWorkTime> eesGetTimeSheetWorkTimeByCode(@RequestParam String worktimeCode) {
        return eesTimesheetWorkTimeService.getTimeSheetWorktimeByCode(worktimeCode);
    }

    @DeleteMapping(EesTimesheetEndpoint.EES_DELETE_BY_CODE_TIMESHEET_WORKTIME)
    public ResponseEntity<Object> eesDeleteTimeSheetWorktimeByCode(@PathVariable String worktimeCode) {
        return eesTimesheetWorkTimeService.deleteTimeSheetWorkTimeByCode(worktimeCode);
    }
}

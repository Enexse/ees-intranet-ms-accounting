package com.enexse.intranet.ms.accounting.controllers;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.EesTimesheetContractHour;
import com.enexse.intranet.ms.accounting.request.EesTimesheetContractHourRequest;
import com.enexse.intranet.ms.accounting.response.EesTimeSheetContractHourResponse;
import com.enexse.intranet.ms.accounting.services.EesTimesheetContractHourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EesTimesheetEndpoint.EES_ROOT_ENDPOINT)
@CrossOrigin("*")
@AllArgsConstructor
public class EesTimesheetContractHourController {
    private EesTimesheetContractHourService timeSheetContractHoursService;

    @PostMapping(EesTimesheetEndpoint.EES_INSERT_TIMESHEET_CONTRACTHOURS)
    public ResponseEntity<Object> eesInsertTimeSheetContractHours(@RequestBody EesTimesheetContractHourRequest request) {
        return timeSheetContractHoursService.insertContractHours(request);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_All_TIMESHEET_CONTRACTHOURS)
    public List<EesTimeSheetContractHourResponse> eesGetAllTimeSheetContractHours() {
        return timeSheetContractHoursService.getAllTimeSheetContractHours();
    }

    @PutMapping(EesTimesheetEndpoint.EES_UPDATE_BY_CODE_TIMESHEET_CONTRACTHOURS)
    public ResponseEntity<Object> eesUpdateTimeSheetContractHoursByCode(@PathVariable String contractHoursCode, @RequestBody EesTimesheetContractHourRequest request) {
        return timeSheetContractHoursService.updateTimeSheetContractHoursByCode(contractHoursCode, request);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_TIMESHEET_CONTRACTHOURS_BY_CODE)
    public Optional<EesTimesheetContractHour> eesGetTimeSheetContractHoursByCode(@PathVariable String contractHoursCode) {
        return timeSheetContractHoursService.getTimeSheetContractHoursByCode(contractHoursCode);
    }

    @DeleteMapping(EesTimesheetEndpoint.EES_DELETE_BY_CODE_TIMESHEET_CONTRACTHOURS)
    public ResponseEntity<Object> eesDeleteTimeSheetContractHoursByCode(@PathVariable String contractHoursCode) {
        return timeSheetContractHoursService.deleteTimeSheetContractHoursByCode(contractHoursCode);
    }
}

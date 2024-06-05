package com.enexse.intranet.ms.accounting.controllers;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.EesTimesheetWorkplace;
import com.enexse.intranet.ms.accounting.request.EesTimesheetWorkPlaceRequest;
import com.enexse.intranet.ms.accounting.response.EesTimeSheetWorkPlaceResponse;
import com.enexse.intranet.ms.accounting.services.EesTimesheetWorkPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EesTimesheetEndpoint.EES_ROOT_ENDPOINT)
@CrossOrigin("*")
@AllArgsConstructor
public class EesTimesheetWorkPlaceController {

    private EesTimesheetWorkPlaceService timeSheetWorkPlaceService;

    @PostMapping(EesTimesheetEndpoint.EES_INSERT_TIMESHEET_WORKPLACE)
    public ResponseEntity<Object> eesInsertTimeSheetWorkPlace(@RequestBody EesTimesheetWorkPlaceRequest request) {
        return timeSheetWorkPlaceService.insertTimeSheetWorkPlace(request);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_All_TIMESHEET_WORKPLACES)
    public List<EesTimeSheetWorkPlaceResponse> eesGetAllTimeSheetWorkPlaces() {
        return timeSheetWorkPlaceService.getAllTimeSheetWorkPlaces();
    }

    @PutMapping(EesTimesheetEndpoint.EES_UPDATE_BY_CODE_TIMESHEET_WORKPLACE)
    public ResponseEntity<Object> eesUpdateTimeSheetWorkPlaceByCode(@PathVariable String workPlaceCode, @RequestBody EesTimesheetWorkPlaceRequest request) {
        return timeSheetWorkPlaceService.updateTimeSheetWorkPlaceByCode(workPlaceCode, request);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_TIMESHEET_WORKPLACE_BY_CODE)
    public Optional<EesTimesheetWorkplace> eesGetTimeSheetWorkPlaceByCode(@RequestParam String workPlaceCode) {
        return timeSheetWorkPlaceService.getTimeSheetWorkPlaceByCode(workPlaceCode);
    }

    @DeleteMapping(EesTimesheetEndpoint.EES_DELETE_BY_CODE_TIMESHEET_WORKPLACE)
    public ResponseEntity<Object> eesDeleteTimeSheetWorkPlaceByCode(@PathVariable String workPlaceCode) {
        return timeSheetWorkPlaceService.deleteTimeSheetWorkPlaceByCode(workPlaceCode);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_WORKPLACE_TITLE)
    public String getTitleByCode(@RequestParam("workPlaceCode") String workPlaceCode) {
        return timeSheetWorkPlaceService.getTitleByCode(workPlaceCode);
    }
}

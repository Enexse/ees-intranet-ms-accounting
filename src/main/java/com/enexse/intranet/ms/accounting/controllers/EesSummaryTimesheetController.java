package com.enexse.intranet.ms.accounting.controllers;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetConstant;
import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.EesSummaryTimesheet;
import com.enexse.intranet.ms.accounting.response.EesPrepareSummaryTimeSheetResponse;
import com.enexse.intranet.ms.accounting.response.EesSummaryTimeSheetResponse;
import com.enexse.intranet.ms.accounting.services.EesReportService;
import com.enexse.intranet.ms.accounting.services.EesTimesheetSummaryService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EesTimesheetEndpoint.EES_ROOT_ENDPOINT)
@CrossOrigin("*")
@AllArgsConstructor
public class EesSummaryTimesheetController {

    @Autowired
    private EesReportService reportService;

    @Autowired
    private EesTimesheetSummaryService eesTimesheetSummaryService;

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_GET_SUMMARY_TIMESHEET)
    public ResponseEntity<Object> generateReport(@PathVariable String userId) throws FileNotFoundException, JRException, IOException {
        return reportService.exportReport(userId);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_GET_ALL_SUMMARY_TIMESHEET)
    public ResponseEntity<Object> getAll() {
        return eesTimesheetSummaryService.getAllTimesheetSummary();
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_GET_SUMMARY_TIMESHEET_BY_USERID_YEAR)
    public Optional<List<EesSummaryTimesheet>> getAllByUserIdAndYear(@PathVariable String userId, @PathVariable String year) {
        return eesTimesheetSummaryService.getAllTimesheetSummaryByUserIdAndYear(userId, year);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_GET_SUMMARY_TIMESHEET_RESPONSE_BY_USERID_YEAR)
    public List<EesSummaryTimeSheetResponse> getAllResponseByUserIdAndYear(@PathVariable String userId, @PathVariable String year) {
        return eesTimesheetSummaryService.getAllTimesheetSummaryResponseByUserId(userId, year);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_GET_SUMMARY_TIMESHEET_BY_USERID)
    public Optional<List<EesSummaryTimesheet>> getAllByUserId(@PathVariable String userId) {
        return eesTimesheetSummaryService.getAllTimesheetSummaryByUserId(userId);
    }

    @PutMapping(EesTimesheetEndpoint.EES_SUBMIT_SUMMARY_TIMESHEET_BY_USERID)
    public ResponseEntity<Object> eesSubmitTimesheetByMonthAndYear(@PathVariable String userId) {
        return eesTimesheetSummaryService.submitTimesheetByMonthAndYear(userId);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @DeleteMapping(EesTimesheetEndpoint.EES_DELETE_SUMMARY_TIMESHEET_ATTACHMENT)
    public ResponseEntity<Object> eesSubmitTimesheetDeleteAttachment(@PathVariable String id, @PathVariable String userId) {
        return eesTimesheetSummaryService.submitTimesheetDeleteAttachment(id, userId);
    }

    @GetMapping(EesTimesheetEndpoint.EES_GET_SUMMARY_TIMESHEET_BY_USERID_MONTH_YEAR)
    public ResponseEntity<Object> eesGetTimesheetByUserIdAndMonthAndYear(@PathVariable String userId) {
        return eesTimesheetSummaryService.getTimesheetByUserIdAndMonthAndYear(userId);
    }

    @RolesAllowed({EesTimesheetConstant.EES_ROLE_ADMINISTRATOR, EesTimesheetConstant.EES_ROLE_RESPONSABLE})
    @PostMapping(EesTimesheetEndpoint.EES_GET_PREPARE_SUMMARY_TIMESHEET)
    public List<EesPrepareSummaryTimeSheetResponse> eesGetPrepareSummaryTimesheet(@RequestBody List<String> collaboratorsId,
                                                                                  @RequestParam("month") String month,
                                                                                  @RequestParam("year") String year) {
        return eesTimesheetSummaryService.getPrepareSummaryTimesheet(collaboratorsId, month, year);
    }

    @RolesAllowed({EesTimesheetConstant.EES_ROLE_ADMINISTRATOR, EesTimesheetConstant.EES_ROLE_RESPONSABLE})
    @PutMapping(EesTimesheetEndpoint.EES_UPDATE_SUBMIT_SUMMARY_TIMESHEET_BY_USERID)
    public ResponseEntity<Object> eesUpdateSubmissionTimesheet(
            @PathVariable String userId,
            @RequestParam("month") String month,
            @RequestParam("year") String year) {
        return eesTimesheetSummaryService.updateSubmissionTimesheet(userId, month, year);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @PutMapping(EesTimesheetEndpoint.EES_ATTACHMENT_FILES_SUMMARY_TIMESHEET_BY_USERID)
    public ResponseEntity<Object> eesAttachmentFilesSubmissionTimesheet(
            @PathVariable String userId,
            @RequestParam("month") String month,
            @RequestParam("year") String year,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> files) {
        return eesTimesheetSummaryService.attachmentFilesSubmissionTimesheet(userId, month, year, files);
    }

    @RolesAllowed({EesTimesheetConstant.EES_ROLE_ADMINISTRATOR, EesTimesheetConstant.EES_ROLE_RESPONSABLE})
    @GetMapping(EesTimesheetEndpoint.EES_GET_SUMMARY_TIMESHEET_BY_MONTH_YEAR)
    public ResponseEntity<Object> eesGetTimesheetByMonthAndYear(@RequestParam("month") String month,
                                                                @RequestParam("year") String year) {
        return eesTimesheetSummaryService.getTimesheetByMonthAndYear(month, year);
    }
}

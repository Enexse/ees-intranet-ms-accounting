package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetConstant;
import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.EesSummaryTimesheet;
import com.enexse.intranet.ms.accounting.models.EesTimesheetDoc;
import com.enexse.intranet.ms.accounting.models.EesUserTimeSheet;
import com.enexse.intranet.ms.accounting.models.partials.EesCloudinaryDoc;
import com.enexse.intranet.ms.accounting.models.partials.EesCustomer;
import com.enexse.intranet.ms.accounting.models.partials.EesUserOrder;
import com.enexse.intranet.ms.accounting.openfeign.EesCloudinaryService;
import com.enexse.intranet.ms.accounting.openfeign.EesCustomerService;
import com.enexse.intranet.ms.accounting.openfeign.EesUserOrderService;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.EesSummaryTimesheetRepository;
import com.enexse.intranet.ms.accounting.repositories.EesTimesheetDocRepository;
import com.enexse.intranet.ms.accounting.repositories.EesUserTimeSheetRepository;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import com.enexse.intranet.ms.accounting.response.EesPrepareSummaryTimeSheetResponse;
import com.enexse.intranet.ms.accounting.response.EesSummaryTimeSheetResponse;
import com.enexse.intranet.ms.accounting.utils.EesCommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EesTimesheetSummaryService {

    private final EesSummaryTimesheetRepository eesSummaryTimesheetRepository;
    private final EesUserTimeSheetRepository eesUserTimeSheetRepository;
    private final EesUserService userService;
    private final EesCustomerService customerService;
    private final EesUserOrderService userOrderService;
    private final EesTimesheetDocRepository docRepository;
    private final EesCloudinaryService cloudinaryService;

    public ResponseEntity<Object> getAllTimesheetSummary() {
        List<EesSummaryTimesheet> list = eesSummaryTimesheetRepository.findAll()
                .stream().sorted(Comparator.comparing(EesSummaryTimesheet::getCreatedAt).reversed()).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    public Optional<List<EesSummaryTimesheet>> getAllTimesheetSummaryByUserIdAndYear(String userId, String year) {
        Optional<List<EesSummaryTimesheet>> list = eesSummaryTimesheetRepository.findByUserIdAndYear(userId, year);
        return list;
    }

    public Optional<List<EesSummaryTimesheet>> getAllTimesheetSummaryByUserId(String userId) {
        Optional<List<EesSummaryTimesheet>> list = eesSummaryTimesheetRepository.findByUserId(userId);
        return list;
    }

    public List<EesSummaryTimeSheetResponse> getAllTimesheetSummaryResponseByUserId(String userId, String year) {
        Optional<List<EesSummaryTimesheet>> list = eesSummaryTimesheetRepository.findByUserIdAndYear(userId, year);
        List<EesSummaryTimeSheetResponse> response = new ArrayList<EesSummaryTimeSheetResponse>();
        for (EesSummaryTimesheet summary : list.get()) {
            List<EesCloudinaryDoc> cloudinaryDocs = new ArrayList<EesCloudinaryDoc>();
            summary.setAttachments(docRepository.findBySummaryIdAndYear(summary.getSummaryId(), year));
            for(EesTimesheetDoc attachment : summary.getAttachments()) {
                cloudinaryDocs.add(cloudinaryService.findAttachment(attachment.getCloudinaryId(), userId));
            }
            EesSummaryTimeSheetResponse summaryResponse = new EesSummaryTimeSheetResponse().builder()
                    .eesSummaryTimesheet(summary)
                    .eesSummaryAttachments(cloudinaryDocs)
                    .build();
            response.add(summaryResponse);
        }
        return response;
    }

    public ResponseEntity<Object> submitTimesheetByMonthAndYear(String userId) {

        Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(userId, EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
        if (summary.isPresent()) {
            summary.get().setTimesheetSent(summary.get().isTimesheetSent() ? Boolean.FALSE.booleanValue() : Boolean.TRUE.booleanValue());
            summary.get().setSentAt(EesCommonUtil.generateCurrentDateUtil());
            eesSummaryTimesheetRepository.save(summary.get());
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_SUBMIT), HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_NOT_FOUND, userId)), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> getTimesheetByUserIdAndMonthAndYear(String userId) {
        Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(userId, EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
        if (summary.isPresent()) {
            return new ResponseEntity<Object>(summary, HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_NOT_FOUND, userId)), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public List<EesPrepareSummaryTimeSheetResponse> getPrepareSummaryTimesheet(List<String> collaboratorsId, String month, String year) {
        List<EesPrepareSummaryTimeSheetResponse> prepareSummary = new ArrayList<EesPrepareSummaryTimeSheetResponse>();
        List<String> collaborators = new ArrayList<>();

        // Retrieve only collaboratorsId in current month and year in the group selected by the admin/responsible
        collaboratorsId.stream().map(collaborator -> {
            if (eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(collaborator, month, year).isPresent()) {
                collaborators.add(collaborator);
            }
            return collaborators;
        }).collect(Collectors.toList());

        // Prepare TimeSheet Report Summary
        collaborators.stream().map(collaborator -> {
            Optional<EesSummaryTimesheet> eesSummaryTimesheet = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(collaborator, month, year);
            Optional<EesUserTimeSheet> userTimeSheet = eesUserTimeSheetRepository.findByUserId(collaborator);
            EesPrepareSummaryTimeSheetResponse response = new EesPrepareSummaryTimeSheetResponse()
                    .builder()
                    .eesSummaryTimesheet(eesSummaryTimesheet.isPresent() ? eesSummaryTimesheet.get() : new EesSummaryTimesheet())
                    .eesUserTimesheet(userTimeSheet.isPresent() ? userTimeSheet.get() : new EesUserTimeSheet())
                    .eesUser(userService.findById(collaborator))
                    .eesCustomer(userTimeSheet.isPresent() && userTimeSheet.get().getCustomerId() != null ? customerService.findById(userTimeSheet.get().getCustomerId()) : new EesCustomer())
                    .eesUserOrder(userTimeSheet.isPresent() && userTimeSheet.get().getNumOrder() != null ? userOrderService.findByCode(userTimeSheet.get().getNumOrder()) : new EesUserOrder())
                    .build();
            return prepareSummary.add(response);
        }).collect(Collectors.toList());
        return prepareSummary;
    }

    public ResponseEntity<Object> updateSubmissionTimesheet(String userId, String month, String year) {
        Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(userId, month, year);
        if (summary.isPresent()) {
            summary.get().setTimesheetSent(Boolean.FALSE.booleanValue());
            summary.get().setSentAt("");
            summary.get().setUpdatedAt("");
            eesSummaryTimesheetRepository.save(summary.get());
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_SUMMARY_UPDATED), HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_SUMMARY_NOT_FOUND, userId)), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Object> getTimesheetByMonthAndYear(String month, String year) {
        Optional<List<EesSummaryTimesheet>> eesSummariesTimesheet = eesSummaryTimesheetRepository.findByMonthAndYear(month, year);
        List<EesPrepareSummaryTimeSheetResponse> prepareSummary = new ArrayList<EesPrepareSummaryTimeSheetResponse>();
        eesSummariesTimesheet.get().stream().map(summary -> {
            Optional<EesSummaryTimesheet> eesSummaryTimesheet = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(summary.getUserId(), month, year);
            Optional<EesUserTimeSheet> userTimeSheet = eesUserTimeSheetRepository.findByUserId(summary.getUserId());
            EesPrepareSummaryTimeSheetResponse response = new EesPrepareSummaryTimeSheetResponse()
                    .builder()
                    .eesSummaryTimesheet(eesSummaryTimesheet.isPresent() ? eesSummaryTimesheet.get() : new EesSummaryTimesheet())
                    .eesUserTimesheet(userTimeSheet.isPresent() ? userTimeSheet.get() : new EesUserTimeSheet())
                    .eesUser(userService.findById(summary.getUserId()))
                    .eesCustomer(userTimeSheet.isPresent() && userTimeSheet.get().getCustomerId() != null ? customerService.findById(userTimeSheet.get().getCustomerId()) : new EesCustomer())
                    .eesUserOrder(userTimeSheet.isPresent() && userTimeSheet.get().getNumOrder() != null ? userOrderService.findByCode(userTimeSheet.get().getNumOrder()) : new EesUserOrder())
                    .build();
            return prepareSummary.add(response);
        }).collect(Collectors.toList());
        return new ResponseEntity<Object>(prepareSummary, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> attachmentFilesSubmissionTimesheet(String userId, String month, String year, List<MultipartFile> files) {
        Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(userId, month, year);
        if (summary.isPresent()) {

            // Save documents to Cloudinary and Mongo DB
            List<EesCloudinaryDoc> attachments = cloudinaryService.saveAll(
                    userId,
                    EesTimesheetConstant.EES_CLOUDINARY_TIMESHEET_TYPE,
                    EesTimesheetConstant.EES_CLOUDINARY_TIMESHEET_FOLDER, files);

            // Save relation documents to PostgreSQL
            List<EesTimesheetDoc> documents = docRepository.saveAll(attachments.stream().map(attachment ->
                    new EesTimesheetDoc().builder()
                            .summaryId(summary.get())
                            .month(month)
                            .year(year)
                            .cloudinaryId(attachment.getCloudinaryId())
                            .build()).collect(Collectors.toList()));

            // Update Summary Timesheet
            summary.get().setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());
            summary.get().setAttachments(documents);
            eesSummaryTimesheetRepository.save(summary.get());
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_SUMMARY_UPDATED), HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_SUMMARY_NOT_FOUND, userId)), HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Object> submitTimesheetDeleteAttachment(String cloudinaryId, String userId) {
        EesTimesheetDoc doc = docRepository.findByCloudinaryId(cloudinaryId);
        if(doc != null) {
            // First, delete relation in PostgreSQL
            docRepository.delete(doc);

            // Second, delete document in Cloudinary storage and MongoDb
            cloudinaryService.deleteAttachment(cloudinaryId);
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_SUMMARY_UPDATED), HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_SUMMARY_NOT_FOUND, userId)), HttpStatus.NOT_FOUND);
    }
}

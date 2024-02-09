package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.partials.EesCustomer;
import com.enexse.intranet.ms.accounting.models.EesSummaryTimesheet;
import com.enexse.intranet.ms.accounting.models.EesUserTimeSheet;
import com.enexse.intranet.ms.accounting.models.partials.EesUserOrder;
import com.enexse.intranet.ms.accounting.openfeign.EesCustomerService;
import com.enexse.intranet.ms.accounting.openfeign.EesUserOrderService;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.EesSummaryTimesheetRepository;
import com.enexse.intranet.ms.accounting.repositories.EesUserTimeSheetRepository;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import com.enexse.intranet.ms.accounting.response.EesPrepareSummaryTimeSheetResponse;
import com.enexse.intranet.ms.accounting.utils.EesCommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ResponseEntity<Object> getAllTimesheetSummary() {
        List<EesSummaryTimesheet> list = eesSummaryTimesheetRepository.findAll();
        return ResponseEntity.ok(list);
    }

    public Optional<List<EesSummaryTimesheet>> getAllTimesheetSummaryByUserId(String userId) {
        Optional<List<EesSummaryTimesheet>> list = eesSummaryTimesheetRepository.findByUserId(userId);
        return list;
    }

    public ResponseEntity<Object> submitTimesheetByMonthAndYear(String userId) {

        Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(userId, EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
        if (summary.isPresent()) {
            summary.get().setTimesheetSent(summary.get().isTimesheetSent() ? Boolean.FALSE.booleanValue() : Boolean.TRUE.booleanValue());
            summary.get().setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());
            eesSummaryTimesheetRepository.save(summary.get());
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_SUBMIT), HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_NOT_FOUND, userId)), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> getTimesheetByMonthAndYear(String userId) {
        Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(userId, EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
        if (summary.isPresent()) {
            return new ResponseEntity<Object>(summary, HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_NOT_FOUND, userId)), HttpStatus.NOT_FOUND);
    }

    public List<EesPrepareSummaryTimeSheetResponse> getPrepareSummaryTimesheet(List<String> collaboratorsId) {
        List<EesPrepareSummaryTimeSheetResponse> prepareSummary = new ArrayList<EesPrepareSummaryTimeSheetResponse>();

        collaboratorsId.stream().map(collaborator -> {
            Optional<EesSummaryTimesheet> eesSummaryTimesheet = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(collaborator, EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
            Optional<EesUserTimeSheet> userTimeSheet = eesUserTimeSheetRepository.findByUserId(collaborator);
            EesPrepareSummaryTimeSheetResponse response = new EesPrepareSummaryTimeSheetResponse()
                    .builder()
                    .eesSummaryTimesheet(eesSummaryTimesheet.isPresent() ? eesSummaryTimesheet.get() : new EesSummaryTimesheet())
                    .eesUserTimesheet(userTimeSheet.isPresent() ? userTimeSheet.get() : new EesUserTimeSheet())
                    .eesUser(userService.findById(collaborator))
                    .eesCustomer(userTimeSheet.isPresent() ? customerService.findById(userTimeSheet.get().getCustomerId()) : new EesCustomer())
                    .eesUserOrder(userTimeSheet.isPresent() ? userOrderService.findByCode(userTimeSheet.get().getNumOrder()) : new EesUserOrder())
                    .build();
            return prepareSummary.add(response);
        }).collect(Collectors.toList());
        return prepareSummary;
    }
}

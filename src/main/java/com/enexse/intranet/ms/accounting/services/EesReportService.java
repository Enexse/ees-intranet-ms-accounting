package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.EesSummaryTimesheet;
import com.enexse.intranet.ms.accounting.models.partials.EesUser;
import com.enexse.intranet.ms.accounting.models.EesUserTimeSheet;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.EesSummaryTimesheetRepository;
import com.enexse.intranet.ms.accounting.repositories.EesUserTimeSheetRepository;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EesReportService {

    private EesSummaryTimesheetRepository eesSummaryTimesheetRepository;
    private EesUserService userService;
    private EesUserTimeSheetRepository userTimeSheetRepository;

    public static String getCurrentYear() {
        LocalDateTime dateTime = LocalDateTime.now();
        int year = dateTime.getYear();
        return String.valueOf(year);
    }

    public ResponseEntity<Object> exportReport(String userId) throws FileNotFoundException, JRException, IOException {

        EesUser user = userService.findById(userId);
        if (user != null) {

            //get user timesheet details
            Optional<EesUserTimeSheet> userTimeSheetOptional = userTimeSheetRepository.findByUserId(userId);
            if (userTimeSheetOptional.isPresent()) {
                EesUserTimeSheet userTimeSheet = null;
                userTimeSheet = userTimeSheetOptional.get();
                List<EesSummaryTimesheet> list = eesSummaryTimesheetRepository.findAll();
                //load the file and compile it
                try {
                    File file = ResourceUtils.getFile("classpath:templates/summary_timesheet.jrxml");
                    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
                    Map<String, Object> param = new HashMap<>();
                    param.put("firstName", user.getFirstName());
                    param.put("lastName", user.getLastName());
                    param.put("year", getCurrentYear());
                    param.put("rtt", userTimeSheet.getRtt() != 0 ? Boolean.TRUE : Boolean.FALSE);
                    param.put("hoursperweek", Float.parseFloat(userTimeSheet.getContractHoursClient().getContractHours()));
                    param.put("hoursperday", userTimeSheet.getContractHoursClient().getContractHoursDay());
                    param.put("summaryTimesheetDatasource", dataSource);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, new JREmptyDataSource());


                    // Export the report to PDF
                    byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

                    // Set the appropriate headers for the response
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    headers.setContentDispositionFormData("attachment", "report.pdf");

                    // Return the response entity with the file content
                    return ResponseEntity.ok()
                            .headers(headers)
                            .contentLength(pdfBytes.length)
                            .body(pdfBytes);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            } else {
                return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_USER_TIMESHEET_NOT_FOUND)), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_USER_NOT_FOUND)), HttpStatus.NOT_FOUND);
        }
    }
}

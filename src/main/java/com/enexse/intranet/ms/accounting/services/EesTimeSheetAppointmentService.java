package com.enexse.intranet.ms.accounting.services;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetResponse;
import com.enexse.intranet.ms.accounting.models.*;
import com.enexse.intranet.ms.accounting.models.partials.EesCustomer;
import com.enexse.intranet.ms.accounting.models.partials.EesRequest;
import com.enexse.intranet.ms.accounting.models.partials.EesSubRequest;
import com.enexse.intranet.ms.accounting.models.partials.EesUser;
import com.enexse.intranet.ms.accounting.openfeign.EesCustomerService;
import com.enexse.intranet.ms.accounting.openfeign.EesRequestService;
import com.enexse.intranet.ms.accounting.openfeign.EesSubRequestService;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.repositories.*;
import com.enexse.intranet.ms.accounting.request.EesTimeSheetAppointmentRequest;
import com.enexse.intranet.ms.accounting.response.EesMessageResponse;
import com.enexse.intranet.ms.accounting.utils.EesCommonUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EesTimeSheetAppointmentService {

    private EesTimeSheetAppointmentRepository appointmentRepository;
    private EesTimeSheetWorkTimeRepository workTimeRepository;
    private EesTimesheetWorkPlaceRepository workplaceRepository;
    private EesTimesheetActivityRepository activityRepository;
    private EesTimesheetContractHourRepository contractHourRepository;
    private EesUserService userService;
    private EesRequestService requestService;
    private EesSubRequestService subRequestService;
    private EesCustomerService customerService;
    private EesSummaryTimesheetRepository eesSummaryTimesheetRepository;
    private EesUserTimeSheetRepository eesUserTimeSheetRepository;

    public ResponseEntity<Object> insertTimeSheetAppointment(EesTimeSheetAppointmentRequest request) {

        // Retrieve the related entities using the provided IDs
        EesTimeSheetWorkTime workTime = workTimeRepository.findById(request.getWorktimeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid worktime ID"));

        EesTimesheetWorkplace workplace = workplaceRepository.findById(request.getWorkplaceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid workplace ID"));

        EesTimeSheetActivity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid activity ID"));

        EesTimesheetContractHour contractHour;
        if (request.getContractHoursId() != null) {
            contractHour = contractHourRepository.findById(request.getContractHoursId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contractHours ID"));
        } else {
            contractHour = contractHourRepository.findByContractHours("40")
                    .orElseThrow(() -> new IllegalArgumentException("Invalid contractHours ID"));
        }

        // Find related user
        EesUser user = userService.findById(request.getUserId());

        EesRequest userRequest;
        if (request.getRequestId() != null && !request.getRequestId().isEmpty()) {
            userRequest = requestService.findById(request.getRequestId());
        } else {
            userRequest = null;
        }

        EesSubRequest userSubRequest;
        if (request.getSubRequestId() != null && !request.getSubRequestId().isEmpty()) {
            userSubRequest = subRequestService.findById(request.getSubRequestId());
        } else {
            userSubRequest = null;
        }

        // Find related customer
        EesCustomer customer = customerService.findById(request.getCustomerId());

        EesTimesheetAppointment appointment = EesTimesheetAppointment.builder()
                .workTime(workTime)
                .workplace(workplace)
                .user(user)
                .userId(user.getUserId())
                .customerId(customer.getCustomerId())
                .customer(customer)
                .requestId(request.getRequestId())
                .request(userRequest)
                .subRequestId(request.getSubRequestId())
                .subRequest(userSubRequest)
                .hoursDay(request.getHoursDay())
                .hoursDayAbsence(request.getHoursDayAbsence())
                .overtime(request.getHoursDay() - 7) // All collaborator contract legal in France is 35 hours/week
                .projectName(request.getProjectName())
                .activity(activity)
                .contractHours(contractHour)
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                .createdAt(EesCommonUtil.generateCurrentDateUtil())
                .updatedAt(EesCommonUtil.generateCurrentDateUtil())
                .build();

        // Save appointment
        appointmentRepository.save(appointment);

        // Summary timesheet for user
        Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(request.getUserId(), EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
        if (summary.isPresent()) {
            // Update
            summary.get().setHoursOfWork(summary.get().getHoursOfWork() + request.getHoursDay());
            summary.get().setAt(summary.get().getHoursOfWork());
            summary.get().setAbsences(summary.get().getAbsences() + request.getHoursDayAbsence());
            summary.get().setTotalDaysWorked(summary.get().getTotalDaysWorked() + 1);
            summary.get().setOvertime(summary.get().getOvertime() + (request.getHoursDay() - 7));
            summary.get().setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());
            eesSummaryTimesheetRepository.save(summary.get());
        } else {
            // Create
            Optional<EesUserTimeSheet> userTimeSheet = eesUserTimeSheetRepository.findByUserId(appointment.getUserId());
            if (userTimeSheet.isPresent()) {

                EesSummaryTimesheet summaryUser = new EesSummaryTimesheet().builder()
                        .userId(appointment.getUserId())
                        .pseudo(getPseudo(userService.findById(appointment.getUserId())))
                        .month(EesCommonUtil.generateCurrentMonthUtil())
                        .year(EesCommonUtil.generateCurrentYearUtil())
                        .absences(appointment.getHoursDayAbsence())
                        .hoursOfWork(appointment.getHoursDay())
                        .overtime(0)
                        .contractHoursClientPerWeek(appointment.getContractHours().getContractHoursId())
                        .totalDaysWorked(1)
                        .at(appointment.getHoursDay())
                        .afternoonIQ(0)
                        .dayIQ(0)
                        .morningIQ(0)
                        .nightIQ(0)
                        .other(0)
                        .isTimesheetSent(Boolean.FALSE)
                        .createdAt(EesCommonUtil.generateCurrentDateUtil())
                        .updatedAt(EesCommonUtil.generateCurrentDateUtil())
                        .build();
                eesSummaryTimesheetRepository.save(summaryUser);
            }
        }

        return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_CREATED), HttpStatus.CREATED);
    }

    private String getPseudo(EesUser user) {
        return user.getLastName() + " " + user.getFirstName();
    }

    public List<EesTimesheetAppointment> getUserAppointments(String userId) {
        List<EesTimesheetAppointment> appointments = appointmentRepository.findByUserIdWithWorkplaceAndActivity(userId);
        appointments.sort(Comparator.comparing(EesTimesheetAppointment::getStartDateTime));
        List<EesTimesheetAppointment> newAppointments = appointments.stream().map(appointment -> {
            appointment.setCustomer(customerService.findById(appointment.getCustomerId()));
            //appointment.setCustomer(new EesCustomer());
            return appointment;
        }).collect(Collectors.toList());
        return newAppointments;
    }

    public ResponseEntity<Object> deleteTimeSheetAppointment(Long id, String userId) {
        EesTimesheetAppointment timesheetAppointment = appointmentRepository.findByIdAndUserId(id, userId);
        if (timesheetAppointment != null) {
            // Summary timesheet for user to update
            Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(userId, EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
            if (summary.isPresent()) {
                // Update
                summary.get().setHoursOfWork(summary.get().getHoursOfWork() - timesheetAppointment.getHoursDay());
                summary.get().setAt(summary.get().getHoursOfWork());
                summary.get().setAbsences(summary.get().getAbsences() - timesheetAppointment.getHoursDayAbsence());
                summary.get().setTotalDaysWorked(summary.get().getTotalDaysWorked() - 1);
                summary.get().setOvertime(summary.get().getOvertime() - timesheetAppointment.getOvertime());
                summary.get().setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());
                eesSummaryTimesheetRepository.save(summary.get());
            }
            appointmentRepository.delete(timesheetAppointment);
            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_DELETED), HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_NOT_FOUND, id)), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> updateTimeSheetAppointment(Long id, float hoursDayOld, float hoursDayAbsenceOld, EesTimeSheetAppointmentRequest request) {
        EesTimesheetAppointment timesheetAppointment = appointmentRepository.findByIdAndUserId(id, request.getUserId());
        if (timesheetAppointment != null) {
            // Retrieve the related entities using the provided IDs
            EesTimeSheetWorkTime workTime = workTimeRepository.findById(request.getWorktimeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid worktime ID"));

            EesTimesheetWorkplace workplace = workplaceRepository.findById(request.getWorkplaceId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid workplace ID"));

            EesTimeSheetActivity activity = activityRepository.findById(request.getActivityId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid activity ID"));

            EesTimesheetContractHour contractHour;
            if (request.getContractHoursId() != null) {
                contractHour = contractHourRepository.findById(request.getContractHoursId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid contractHours ID"));
            } else {
                contractHour = contractHourRepository.findByContractHours("40")
                        .orElseThrow(() -> new IllegalArgumentException("Invalid contractHours ID"));
            }

            // Find related user
            EesUser user = userService.findById(request.getUserId());

            EesRequest userRequest;
            if (request.getRequestId() != null && !request.getRequestId().isEmpty()) {
                userRequest = requestService.findById(request.getRequestId());
            } else {
                userRequest = null;
            }

            EesSubRequest userSubRequest;
            if (request.getSubRequestId() != null && !request.getSubRequestId().isEmpty()) {
                userSubRequest = subRequestService.findById(request.getSubRequestId());
            } else {
                userSubRequest = null;
            }

            // Find related customer
            EesCustomer customer = customerService.findById(request.getCustomerId());

            timesheetAppointment.setWorkTime(workTime);
            timesheetAppointment.setWorkplace(workplace);
            timesheetAppointment.setContractHours(contractHour);
            timesheetAppointment.setUser(user);
            timesheetAppointment.setUserId(user.getUserId());
            timesheetAppointment.setCustomerId(customer.getCustomerId());
            timesheetAppointment.setCustomer(customer);
            timesheetAppointment.setRequestId(request.getRequestId());
            timesheetAppointment.setRequest(userRequest);
            timesheetAppointment.setSubRequestId(request.getSubRequestId());
            timesheetAppointment.setSubRequest(userSubRequest);
            timesheetAppointment.setHoursDay(request.getHoursDay());
            timesheetAppointment.setHoursDayAbsence(request.getHoursDayAbsence());
            timesheetAppointment.setOvertime(request.getHoursDay() - 7);
            timesheetAppointment.setProjectName(request.getProjectName());
            timesheetAppointment.setActivity(activity);
            timesheetAppointment.setStartDateTime(request.getStartDateTime());
            timesheetAppointment.setEndDateTime(request.getEndDateTime());
            timesheetAppointment.setUpdatedAt(EesCommonUtil.generateCurrentDateUtil());

            appointmentRepository.save(timesheetAppointment);

            // Update Summary timesheet for user
            Optional<EesSummaryTimesheet> summary = eesSummaryTimesheetRepository.findByUserIdAndMonthAndYear(request.getUserId(), EesCommonUtil.generateCurrentMonthUtil(), EesCommonUtil.generateCurrentYearUtil());
            if (summary.isPresent()) {
                summary.get().setHoursOfWork((summary.get().getHoursOfWork() - hoursDayOld) + request.getHoursDay());
                summary.get().setAt(summary.get().getHoursOfWork());
                summary.get().setAbsences((summary.get().getAbsences() - hoursDayAbsenceOld) + request.getHoursDayAbsence());
                summary.get().setOvertime((summary.get().getOvertime() - (hoursDayOld - 7)) + (request.getHoursDay() - 7));
                summary.get().setTotalDaysWorked(summary.get().getTotalDaysWorked());
                eesSummaryTimesheetRepository.save(summary.get());
            }

            return new ResponseEntity<Object>(new EesMessageResponse(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_UPDATED), HttpStatus.OK);
        }
        return new ResponseEntity<Object>(new EesMessageResponse(String.format(EesTimesheetResponse.EES_TIMESHEET_APPOINTMENT_NOT_FOUND, id)), HttpStatus.NOT_FOUND);
    }
}

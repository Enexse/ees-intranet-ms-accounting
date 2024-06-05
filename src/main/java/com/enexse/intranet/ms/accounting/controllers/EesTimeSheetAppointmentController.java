package com.enexse.intranet.ms.accounting.controllers;


import com.enexse.intranet.ms.accounting.constants.EesTimesheetConstant;
import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.EesTimesheetAppointment;
import com.enexse.intranet.ms.accounting.models.partials.EesCustomer;
import com.enexse.intranet.ms.accounting.models.partials.EesSubRequest;
import com.enexse.intranet.ms.accounting.models.partials.EesUser;
import com.enexse.intranet.ms.accounting.openfeign.EesCustomerService;
import com.enexse.intranet.ms.accounting.openfeign.EesSubRequestService;
import com.enexse.intranet.ms.accounting.openfeign.EesUserService;
import com.enexse.intranet.ms.accounting.request.EesTimeSheetAppointmentRequest;
import com.enexse.intranet.ms.accounting.services.EesTimeSheetAppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(EesTimesheetEndpoint.EES_ROOT_ENDPOINT)
@CrossOrigin("*")
@AllArgsConstructor
public class EesTimeSheetAppointmentController {

    private final EesUserService userService;
    private final EesSubRequestService subRequestService;
    private final EesCustomerService customerService;
    private EesTimeSheetAppointmentService eesTimeSheetAppointmentService;

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @PostMapping(EesTimesheetEndpoint.EES_INSERT_TIMESHEET_APPOINTMENT)
    public ResponseEntity<Object> eesInsertTimeSheetAppointment(@RequestBody EesTimeSheetAppointmentRequest request) {
        return eesTimeSheetAppointmentService.insertTimeSheetAppointment(request);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @PostMapping(EesTimesheetEndpoint.EES_INSERT_MASSIVE_TIMESHEET_APPOINTMENT)
    public ResponseEntity<Object> eesInsertMassiveTimeSheetAppointment(@RequestBody EesTimeSheetAppointmentRequest[] request) {
        return eesTimeSheetAppointmentService.insertMassiveTimeSheetAppointment(request);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_GET_TIMESHEET_APPOINTMENT_BY_USERID)
    public List<EesTimesheetAppointment> eesGetTimeSheetActivityByCode(@RequestParam String userId) {
        return eesTimeSheetAppointmentService.getUserAppointments(userId);
    }

    //TEST THE COMMUNICATION BETWEEN THE MICROSERVICES
    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping("/accounting/{userId}")
    public EesUser getUserById(@PathVariable String userId) {
        // Call the getUserById method from the user service
        EesUser user = userService.findById(userId);

        return user;
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping("/accounting/getSubRequest/{subRequestId}")
    public EesSubRequest getById(@PathVariable String subRequestId) {
        EesSubRequest subRequest = subRequestService.findById(subRequestId);
        return subRequest;
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping("/accounting/getCustomer/{customerId}")
    public EesCustomer getCustomerById(@PathVariable String customerId) {
        EesCustomer customer = customerService.findById(customerId);
        return customer;
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @DeleteMapping(EesTimesheetEndpoint.EES_DELETE_TIMESHEET_APPOINTMENT)
    public ResponseEntity<Object> eesDeleteTimeSheetAppointment(@RequestParam Long id, @PathVariable String userId) {
        return eesTimeSheetAppointmentService.deleteTimeSheetAppointment(id, userId);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @PutMapping(EesTimesheetEndpoint.EES_UPDATE_TIMESHEET_APPOINTMENT)
    public ResponseEntity<Object> eesUpdateTimeSheetAppointment(@RequestBody EesTimeSheetAppointmentRequest request,
                                                                @RequestParam Long id, @RequestParam float hoursDayOld,
                                                                @RequestParam float hoursDayAbsenceOld) {
        return eesTimeSheetAppointmentService.updateTimeSheetAppointment(id, hoursDayOld, hoursDayAbsenceOld, request);
    }
}

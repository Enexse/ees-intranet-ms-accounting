package com.enexse.intranet.ms.accounting.controllers;


import com.enexse.intranet.ms.accounting.constants.EesTimesheetConstant;
import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.EesUserTimeSheet;
import com.enexse.intranet.ms.accounting.request.EesUserTimeSheetRequest;
import com.enexse.intranet.ms.accounting.services.EesUserTimesheetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(EesTimesheetEndpoint.EES_ROOT_ENDPOINT)
@CrossOrigin("*")
@AllArgsConstructor
public class EesUserTimeSheetController {
    private EesUserTimesheetService userTimesheetService;

    @RolesAllowed({EesTimesheetConstant.EES_ROLE_ADMINISTRATOR, EesTimesheetConstant.EES_ROLE_RESPONSABLE})
    @PutMapping(EesTimesheetEndpoint.EES_UPDATE_USER)
    public ResponseEntity<Object> eesUpdateUser(@PathVariable String userId,
                                                @RequestBody EesUserTimeSheetRequest request) {
        return userTimesheetService.updateUser(userId, request);
    }

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_USER_TIMESHEET)
    public EesUserTimeSheet eesGetUserTimeSheet(@RequestParam String userId) {
        return userTimesheetService.getUserTimesheet(userId);
    }
}
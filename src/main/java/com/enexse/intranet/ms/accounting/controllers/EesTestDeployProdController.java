package com.enexse.intranet.ms.accounting.controllers;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.EesTimesheetContractHour;
import com.enexse.intranet.ms.accounting.repositories.EesTimesheetContractHourRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(EesTimesheetEndpoint.EES_ROOT_ENDPOINT)
@CrossOrigin("*")
@AllArgsConstructor
public class EesTestDeployProdController {

    @Autowired
    private EesTimesheetContractHourRepository contractHoursRepository;

    @GetMapping("/test")
    public String getMessage() {
        return "Hello World Accounting Service";
    }

    @GetMapping("/contractHours")
    public List<EesTimesheetContractHour> getContractHours() {
        List<EesTimesheetContractHour> contractHours = contractHoursRepository.findAll();
        return contractHours;
    }
}

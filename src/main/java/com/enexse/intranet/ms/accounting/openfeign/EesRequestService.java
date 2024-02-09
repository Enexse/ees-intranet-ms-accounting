package com.enexse.intranet.ms.accounting.openfeign;

import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.configurations.UsersFeignClientConfig;
import com.enexse.intranet.ms.accounting.constants.EesTimesheetConstant;
import com.enexse.intranet.ms.accounting.models.partials.EesRequest;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;

@Service
@FeignClient(name = EesTimesheetConstant.EES_APP_NAME_USERS)
@LoadBalancerClient(name = EesTimesheetConstant.EES_APP_NAME_USERS, configuration = UsersFeignClientConfig.class)
public interface EesRequestService {

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_USERS_ENDPOINT + EesTimesheetEndpoint.EES_GET_REQUEST_BY_ID)
    EesRequest findById(@PathVariable String requestId);

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(EesTimesheetEndpoint.EES_USERS_ENDPOINT + "/api/v1/request/getByCode/{requestCode}")
    EesRequest findByCode(@RequestParam String requestCode);
}

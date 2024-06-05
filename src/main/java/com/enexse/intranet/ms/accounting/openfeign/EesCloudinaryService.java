package com.enexse.intranet.ms.accounting.openfeign;

import com.enexse.intranet.ms.accounting.configurations.UsersFeignClientConfig;
import com.enexse.intranet.ms.accounting.constants.EesTimesheetConstant;
import com.enexse.intranet.ms.accounting.constants.EesTimesheetEndpoint;
import com.enexse.intranet.ms.accounting.models.partials.EesCloudinaryDoc;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Service
@FeignClient(name = EesTimesheetConstant.EES_APP_NAME_USERS)
@LoadBalancerClient(name = EesTimesheetConstant.EES_APP_NAME_USERS, configuration = UsersFeignClientConfig.class)
public interface EesCloudinaryService {

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @PutMapping(value = EesTimesheetEndpoint.EES_USERS_ENDPOINT + EesTimesheetEndpoint.EES_ATTACHMENT_FILES_USER, consumes = "multipart/form-data")
    List<EesCloudinaryDoc> saveAll(
            @PathVariable String userId,
            @RequestParam String uploadType,
            @RequestParam String folder,
            @RequestPart List<MultipartFile> attachments);

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @GetMapping(value = EesTimesheetEndpoint.EES_USERS_ENDPOINT + EesTimesheetEndpoint.EES_ATTACHMENT_FILE_BY_ID_USERID)
    EesCloudinaryDoc findAttachment(
            @PathVariable String id,
            @PathVariable String userId);

    @RolesAllowed(EesTimesheetConstant.EES_DEFAULT_ROLES)
    @DeleteMapping(value = EesTimesheetEndpoint.EES_USERS_ENDPOINT + EesTimesheetEndpoint.EES_ATTACHMENT_FILE_DELETE_BY_ID)
    EesCloudinaryDoc deleteAttachment(@RequestParam String id);
}

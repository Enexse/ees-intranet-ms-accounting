package com.enexse.intranet.ms.accounting.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesUserTimeSheetRequest {

    private int rtt;
    private String userId;
    private String businessManagerType;
    private String businessManagerEnexse;
    private String businessManagerClient;
    private String numAffair;
    private String numOrder;
    //private String projectName;
    private List<String> projects;
    private String customerId;
    private Long activityId;
    private Long contractHoursClientId;
    private Long contractHoursEnexseId;
    private Long workplaceId;
    private Long worktimeId;
}


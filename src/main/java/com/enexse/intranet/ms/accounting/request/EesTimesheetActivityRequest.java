package com.enexse.intranet.ms.accounting.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesTimesheetActivityRequest {

    @NonNull
    private String activityCode;
    private String activityDesignation;
    private String activityObservation;
    private String userId;
}

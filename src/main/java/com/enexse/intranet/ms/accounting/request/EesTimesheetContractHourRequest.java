package com.enexse.intranet.ms.accounting.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesTimesheetContractHourRequest {

    @NonNull
    private String contractHours;
    private String userId;
}

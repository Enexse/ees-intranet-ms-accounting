package com.enexse.intranet.ms.accounting.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesTimesheetWorkPlaceRequest {

    @NonNull
    private String workPlaceCode;
    private String workPlaceDesignation;
    private String workPlaceObservation;
    private String userId;
}

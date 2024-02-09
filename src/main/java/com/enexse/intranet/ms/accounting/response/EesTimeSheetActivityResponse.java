package com.enexse.intranet.ms.accounting.response;

import com.enexse.intranet.ms.accounting.models.partials.EesUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesTimeSheetActivityResponse {

    private Long activityId;
    private String activityCode;
    private String activityDesignation;
    private String activityObservation;
    private EesUser createdBy;
    private String createdAt;
    private String updatedAt;
}

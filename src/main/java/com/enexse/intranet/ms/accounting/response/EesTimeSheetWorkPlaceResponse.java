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
public class EesTimeSheetWorkPlaceResponse {

    private Long workPlaceId;
    private String workPlaceCode;
    private String workPlaceDesignation;
    private String workPlaceObservation;
    private EesUser createdBy;
    private String createdAt;
    private String updatedAt;

}

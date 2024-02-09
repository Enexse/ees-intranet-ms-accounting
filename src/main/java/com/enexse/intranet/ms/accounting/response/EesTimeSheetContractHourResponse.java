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
public class EesTimeSheetContractHourResponse {

    private Long contractHoursId;
    private String contractHoursCode;
    private String contractHours;
    private float contractHoursDay;
    private EesUser createdBy;
    private String createdAt;
    private String updatedAt;

}

package com.enexse.intranet.ms.accounting.response;

import com.enexse.intranet.ms.accounting.models.EesSummaryTimesheet;
import com.enexse.intranet.ms.accounting.models.partials.EesCloudinaryDoc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesSummaryTimeSheetResponse {

    private EesSummaryTimesheet eesSummaryTimesheet;
    private List<EesCloudinaryDoc> eesSummaryAttachments;
}

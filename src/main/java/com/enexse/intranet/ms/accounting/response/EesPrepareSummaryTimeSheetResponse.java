package com.enexse.intranet.ms.accounting.response;

import com.enexse.intranet.ms.accounting.models.partials.EesCustomer;
import com.enexse.intranet.ms.accounting.models.EesSummaryTimesheet;
import com.enexse.intranet.ms.accounting.models.partials.EesUser;
import com.enexse.intranet.ms.accounting.models.EesUserTimeSheet;
import com.enexse.intranet.ms.accounting.models.partials.EesUserOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesPrepareSummaryTimeSheetResponse {

    private EesSummaryTimesheet eesSummaryTimesheet;
    private EesUserTimeSheet eesUserTimesheet;
    private EesUser eesUser;
    private EesCustomer eesCustomer;
    private EesUserOrder eesUserOrder;
}

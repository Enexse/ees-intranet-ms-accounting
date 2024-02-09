package com.enexse.intranet.ms.accounting.models.partials;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesCustomer implements Serializable {

    private String customerId;
    private String customerCode;
    private String customerTitle;
    private String sectorField;
    private String status;
    private String landline;
    private String fax;
    private String website;
}

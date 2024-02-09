package com.enexse.intranet.ms.accounting.models.partials;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesUserOrder implements Serializable {

    private String id;
    private String code;
    private EesCustomer customer;
    private String from;
    private String to;
    private String createdAt;
    private String updatedAt;
}

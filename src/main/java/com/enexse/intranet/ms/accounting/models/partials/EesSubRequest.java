package com.enexse.intranet.ms.accounting.models.partials;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesSubRequest implements Serializable {

    private String subRequestId;
    private String subRequestCode;
    private String description;
}

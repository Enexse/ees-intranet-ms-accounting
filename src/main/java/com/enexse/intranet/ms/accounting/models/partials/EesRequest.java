package com.enexse.intranet.ms.accounting.models.partials;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesRequest implements Serializable {

    private String requestId;
    private String requestCode;
    private String requestTitle;
}

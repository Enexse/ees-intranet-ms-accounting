package com.enexse.intranet.ms.accounting.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesTimeSheetWorkTimeRequest {

    private String worktimeCode;
    private String worktimeTitle;
    private String worktimeDescription;
    private String userId;
}

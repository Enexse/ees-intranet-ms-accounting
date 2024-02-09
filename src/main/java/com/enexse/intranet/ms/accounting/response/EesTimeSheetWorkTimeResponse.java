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
public class EesTimeSheetWorkTimeResponse {

    private Long worktimeId;
    private String worktimeCode;
    private String worktimeTitle;
    private String worktimeDescription;
    private EesUser createdBy;
    private String createdAt;
    private String updatedAt;
}

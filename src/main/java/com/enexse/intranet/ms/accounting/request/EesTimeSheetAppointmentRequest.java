package com.enexse.intranet.ms.accounting.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesTimeSheetAppointmentRequest {

    private String userId;
    private String requestId;
    private String subRequestId;
    private String customerId;
    private String projectName;
    private float hoursDay;
    private float hoursDayAbsence;
    private float overtime;
    private Long worktimeId;
    private Long workplaceId;
    private Long activityId;
    private Long contractHoursId;
    private Date startDateTime;
    private Date endDateTime;
    private String team;
    private String slotTime;
    private String entryTime;
    private BigDecimal breakTime;
    private String exitTime;
}

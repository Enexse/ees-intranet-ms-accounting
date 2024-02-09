package com.enexse.intranet.ms.accounting.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EesTimesheetAppointmentDTO {

    private Long id;
    private String userId;
    private String customerName;
    private String requestTitle;
    private String subRequestTitle;
    private String projectName;
    private float hoursDay;
    private String activityName;
    private String workplaceName;
    private String worktimeTitle;
    private Date startDateTime;
    private Date endDateTime;
    private String createdAt;
    private String updatedAt;
}

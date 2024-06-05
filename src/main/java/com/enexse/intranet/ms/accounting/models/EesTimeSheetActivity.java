package com.enexse.intranet.ms.accounting.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ees_timesheet_activities")
public class EesTimeSheetActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @Column(name = "activityCode")
    private String activityCode;

    @Column(name = "activityDesignation")
    private String activityDesignation;

    @Column(name = "activityObservation")
    private String activityObservation;

    @ToString.Exclude
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EesTimesheetAppointment> appointments;

    @ToString.Exclude
    @OneToMany(mappedBy = "timeSheetActivity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EesUserTimeSheet> userTimeSheets;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    @Column(name = "userId")
    private String userId;
}

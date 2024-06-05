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
@Table(name = "ees_summary_timesheets")
public class EesSummaryTimesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long summaryId;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private String year;

    @Column(name = "hoursOfWork")
    private float hoursOfWork;

    @Column(name = "absences")
    private float absences;

    @Column(name = "overtime")
    private float overtime;

    @Column(name = "contractHoursClientPerWeek")
    private float contractHoursClientPerWeek;

    @Column(name = "at")
    private float at;

    @Column(name = "dayIQ")
    private float dayIQ;

    @Column(name = "morningIQ")
    private float morningIQ;

    @Column(name = "afternoonIQ")
    private float afternoonIQ;

    @Column(name = "nightIQ")
    private float nightIQ;

    @Column(name = "other")
    private float other;

    @Column(name = "totalDaysWorked")
    private float totalDaysWorked;

    @Column(name = "isTimesheetSent")
    private boolean isTimesheetSent;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    @Column(name = "sentAt")
    private String sentAt;

    //@ToString.Exclude
    @ManyToMany(mappedBy = "summaryId", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<EesTimesheetDoc> attachments;
}

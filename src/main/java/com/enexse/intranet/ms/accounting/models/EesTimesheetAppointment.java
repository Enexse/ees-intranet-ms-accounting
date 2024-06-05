package com.enexse.intranet.ms.accounting.models;

import com.enexse.intranet.ms.accounting.models.partials.EesCustomer;
import com.enexse.intranet.ms.accounting.models.partials.EesRequest;
import com.enexse.intranet.ms.accounting.models.partials.EesSubRequest;
import com.enexse.intranet.ms.accounting.models.partials.EesUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ees_timesheet_appointments")
public class EesTimesheetAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private EesUser user;

    private String userId;

    @Transient
    private EesRequest request;
    private String requestId;

    @Transient
    private EesSubRequest subRequest;
    private String subRequestId;

    @Transient
    private EesCustomer customer;

    private String customerId;
    private String projectName;
    private float hoursDay;
    private float hoursDayAbsence;
    private float overtime;

    @Column(name = "team")
    private String team;

    @Column(name = "slot_time")
    private String slotTime;

    @Column(name = "entry_time")
    private String entryTime;

    @Column(name = "break_time")
    private BigDecimal breakTime;

    @Column(name = "exit_time")
    private String exitTime;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "activity_id")
    @JsonManagedReference
    private EesTimeSheetActivity activity;

    @ManyToOne
    @JoinColumn(name = "workplace_id")
    @JsonManagedReference
    private EesTimesheetWorkplace workplace;

    @ManyToOne
    @JoinColumn(name = "worktime_id")
    @JsonManagedReference
    private EesTimeSheetWorkTime workTime;

    @ManyToOne
    @JoinColumn(name = "contract_hours_id")
    @JsonManagedReference
    private EesTimesheetContractHour contractHours;

    @Column(name = "startDateTime")
    private Date startDateTime;

    @Column(name = "endDateTime")
    private Date endDateTime;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;
}

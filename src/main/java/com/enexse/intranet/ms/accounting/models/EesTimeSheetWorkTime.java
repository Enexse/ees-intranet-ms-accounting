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
@Table(name = "ees_timesheet_worktimes")
public class EesTimeSheetWorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long worktimeId;

    @Column(name = "worktimeCode")
    private String worktimeCode;

    @Column(name = "worktimeTitle")
    private String worktimeTitle;

    @Column(name = "workTimDescription")
    private String worktimeDescription;

    @ToString.Exclude
    @OneToMany(mappedBy = "workTime")
    @JsonIgnore
    private List<EesTimesheetAppointment> appointments;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    @Column(name = "userId")
    private String userId;
}

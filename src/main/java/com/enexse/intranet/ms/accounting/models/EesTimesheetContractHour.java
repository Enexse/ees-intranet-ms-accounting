package com.enexse.intranet.ms.accounting.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ees_timesheet_contract_hours")
public class EesTimesheetContractHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractHoursId;

    @Column(name = "contractHoursCode")
    private String contractHoursCode;

    @Column(name = "contractHours")
    private String contractHours;

    @Column(name = "contractHoursDay")
    private float contractHoursDay;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    @Column(name = "userId")
    private String userId;
}

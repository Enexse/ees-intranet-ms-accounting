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
@Table(name = "ees_user_timesheets")
public class EesUserTimeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTimesheetId;

    private int rtt;
    private String businessManagerType;
    private String businessManagerEnexse;
    private String businessManagerClient;
    private String userId;
    private String numAffair;
    private String numOrder;
    private String projectName;
    private String customerId;

    @ManyToMany
    @JoinColumn(name = "user_id")
    private List<EesTimesheetProject> timeSheetProjects;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private EesTimeSheetActivity timeSheetActivity;

    @ManyToOne
    @JoinColumn(name = "contract_hours_client_id")
    private EesTimesheetContractHour contractHoursClient;

    @ManyToOne
    @JoinColumn(name = "contract_hours_enexse_id")
    private EesTimesheetContractHour contractHoursEnexse;

    @ManyToOne
    @JoinColumn(name = "workplace_id")
    private EesTimesheetWorkplace workplace;

    @ManyToOne
    @JoinColumn(name = "worktime_id")
    private EesTimeSheetWorkTime worktime;
}

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
@Table(name = "ees_timesheet_workplaces")
public class EesTimesheetWorkplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workPlaceId;

    @Column(name = "workPlaceCode")
    private String workPlaceCode;

    @Column(name = "workPlaceDesignation")
    private String workPlaceDesignation;

    @Column(name = "workPlaceObservation")
    private String workPlaceObservation;

    @ToString.Exclude
    @OneToMany(mappedBy = "workplace")
    @JsonIgnore
    private List<EesTimesheetAppointment> appointments;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;

    @Column(name = "userId")
    private String userId;
}

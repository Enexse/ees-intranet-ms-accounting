package com.enexse.intranet.ms.accounting.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ees_timesheet_docs")
public class EesTimesheetDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId;

    @ManyToOne
    @JoinColumn(name = "summary_id")
    private EesSummaryTimesheet summaryId;

    private String cloudinaryId;
    private String month;
    private String year;
}

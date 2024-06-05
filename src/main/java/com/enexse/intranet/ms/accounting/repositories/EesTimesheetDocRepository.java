package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesTimesheetDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EesTimesheetDocRepository extends JpaRepository<EesTimesheetDoc, Long> {

    EesTimesheetDoc findByCloudinaryId(String cloudinaryId);

    @Query("SELECT doc FROM EesTimesheetDoc doc WHERE doc.summaryId.summaryId = ?1 AND doc.year = ?2")
    List<EesTimesheetDoc> findBySummaryIdAndYear(Long summaryId, String year);
}

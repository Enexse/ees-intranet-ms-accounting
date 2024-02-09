package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesSummaryTimesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EesSummaryTimesheetRepository extends JpaRepository<EesSummaryTimesheet, Long> {

    @Query("SELECT summary FROM EesSummaryTimesheet summary WHERE summary.userId = ?1 AND summary.month = ?2 AND summary.year = ?3")
    Optional<EesSummaryTimesheet> findByUserIdAndMonthAndYear(String userId, String month, String year);

    @Query("select summary from EesSummaryTimesheet summary where summary.userId like %?1")
    Optional<List<EesSummaryTimesheet>> findByUserId(String userId);
}

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

    @Query("SELECT summary FROM EesSummaryTimesheet summary WHERE summary.userId = ?1 AND summary.year = ?2")
    Optional<List<EesSummaryTimesheet>> findByUserIdAndYear(String userId, String year);

    @Query("SELECT summary FROM EesSummaryTimesheet summary WHERE summary.userId like %?1")
    Optional<List<EesSummaryTimesheet>> findByUserId(String userId);

    @Query("SELECT summary FROM EesSummaryTimesheet summary WHERE summary.month = ?1 AND summary.year = ?2")
    Optional<List<EesSummaryTimesheet>> findByMonthAndYear(String month, String year);
}

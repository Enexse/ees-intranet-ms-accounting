package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesTimeSheetActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EesTimesheetActivityRepository extends JpaRepository<EesTimeSheetActivity,Long> {

    @Query("select activity from EesTimeSheetActivity activity where activity.activityCode like %?1")
    Optional<EesTimeSheetActivity> findByCode(String code);
}

package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesTimeSheetWorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EesTimeSheetWorkTimeRepository extends JpaRepository<EesTimeSheetWorkTime, Long> {

    @Query("select worktime from EesTimeSheetWorkTime worktime where worktime.worktimeCode like %?1")
    Optional<EesTimeSheetWorkTime> findByCode(String code);
}

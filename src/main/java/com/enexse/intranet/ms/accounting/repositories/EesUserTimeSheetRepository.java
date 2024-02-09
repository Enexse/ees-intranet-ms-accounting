package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesUserTimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EesUserTimeSheetRepository extends JpaRepository<EesUserTimeSheet,Long> {

    @Query("select userTimesheet from  EesUserTimeSheet userTimesheet where userTimesheet.userId like %?1")
    Optional<EesUserTimeSheet> findByUserId(String userId);
}

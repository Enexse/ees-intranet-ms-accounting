package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesTimesheetWorkplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EesTimesheetWorkPlaceRepository extends JpaRepository<EesTimesheetWorkplace, Long> {

    @Query("select workplace from EesTimesheetWorkplace workplace where workplace.workPlaceCode like %?1")
    Optional<EesTimesheetWorkplace> findByCode(String code);
}

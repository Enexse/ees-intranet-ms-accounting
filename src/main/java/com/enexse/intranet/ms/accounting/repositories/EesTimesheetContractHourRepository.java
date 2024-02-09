package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesTimesheetContractHour;
import com.enexse.intranet.ms.accounting.models.EesTimesheetWorkplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EesTimesheetContractHourRepository extends JpaRepository<EesTimesheetContractHour,Long> {

    @Query("select contractHour from EesTimesheetContractHour contractHour where contractHour.contractHoursCode like %?1")
    Optional<EesTimesheetContractHour> findByCode(String code);

    @Query("select contractHour from EesTimesheetContractHour contractHour where contractHour.contractHours like %?1")
    Optional<EesTimesheetContractHour> findByContractHours(String hours);
}

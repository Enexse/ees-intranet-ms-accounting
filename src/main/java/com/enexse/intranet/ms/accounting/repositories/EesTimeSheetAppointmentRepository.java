package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesTimesheetAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EesTimeSheetAppointmentRepository extends JpaRepository<EesTimesheetAppointment, Long> {
    List<EesTimesheetAppointment> findByUserId(String userId);

    @Query("SELECT a FROM EesTimesheetAppointment a JOIN FETCH a.workplace JOIN FETCH  a.activity  JOIN FETCH a.workTime WHERE a.userId = :userId")
    List<EesTimesheetAppointment> findByUserIdWithWorkplaceAndActivity(String userId);

    EesTimesheetAppointment findByIdAndUserId(Long id, String userId);

}

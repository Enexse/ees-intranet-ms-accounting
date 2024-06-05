package com.enexse.intranet.ms.accounting.repositories;

import com.enexse.intranet.ms.accounting.models.EesTimesheetProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EesTimesheetProjectRepository extends JpaRepository<EesTimesheetProject, Long> {

    List<EesTimesheetProject> findAllByUserId(String userId);
}

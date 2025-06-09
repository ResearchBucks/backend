package org.researchbucks.AdminService_API.repository;

import org.researchbucks.AdminService_API.model.Respondent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RespondentRepository extends JpaRepository<Respondent, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Respondent r SET r.isLocked = ?1 WHERE r.id = ?2")
    void updateLockStatus(Boolean isLocked, Long respondentId);
}

package org.researchbucks.AdminService_API.repository;

import org.researchbucks.AdminService_API.model.UserSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface UserSurveyRepository extends JpaRepository<UserSurvey, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE UserSurvey s SET s.isVerified = ?1 WHERE s.id = ?2")
    void approveSurvey(Boolean isVerified, Long surveyId);
}

package org.researchbucks.ResearcherService_API.repository;

import org.researchbucks.ResearcherService_API.model.UserSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserSurveyRepository extends JpaRepository<UserSurvey, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE UserSurvey u SET u.isDeleted = :isDeleted WHERE u.id = :id")
    void updateIsDeleted(@Param("id") Long id, @Param("isDeleted") Boolean isDeleted);
}

package org.researchbucks.repository;

import org.researchbucks.model.UserSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<UserSurvey, Long>{

    List<UserSurvey> getAllByIsDeletedAndIsVerified(Boolean isDeleted, Boolean isVerified);
}

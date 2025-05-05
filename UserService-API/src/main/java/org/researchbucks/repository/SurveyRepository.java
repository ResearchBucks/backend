package org.researchbucks.repository;

import org.researchbucks.model.UserSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<UserSurvey, Long>{

}

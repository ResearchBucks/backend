package org.researchbucks.repository;

import org.researchbucks.model.Survey;
import org.researchbucks.model.SurveyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>{

}

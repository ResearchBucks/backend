package org.researchbucks.repository;

import org.researchbucks.model.SurveyData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyDataRepository extends MongoRepository<SurveyData, Long> {

}

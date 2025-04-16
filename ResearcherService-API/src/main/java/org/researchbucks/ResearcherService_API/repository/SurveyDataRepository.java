package org.researchbucks.ResearcherService_API.repository;

import org.researchbucks.ResearcherService_API.model.SurveyData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyDataRepository extends MongoRepository<SurveyData, String> {
}

package org.researchbucks.ResearcherService_API.repository;

import org.researchbucks.ResearcherService_API.model.SurveyData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyDataRepository extends MongoRepository<SurveyData, String> {

    List<Object> getAllBySurveyId(Long surveyId);
}

package org.researchbucks.ResearcherService_API.repository;

import org.researchbucks.ResearcherService_API.model.SurveyQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyQuestionRepository extends MongoRepository<SurveyQuestion, String> {

    Object getAllBySurveyId(Long surveyId);

    SurveyQuestion getBySurveyId(Long surveyId);

    void deleteBySurveyId(Long surveyId);
}

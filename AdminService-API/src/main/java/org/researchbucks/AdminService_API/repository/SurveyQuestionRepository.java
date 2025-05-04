package org.researchbucks.AdminService_API.repository;

import org.researchbucks.AdminService_API.model.SurveyQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SurveyQuestionRepository extends MongoRepository<SurveyQuestion, String> {

    List<SurveyQuestion> getSurveyQuestionsBySurveyId(Long surveyId);
}

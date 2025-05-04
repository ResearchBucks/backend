package org.researchbucks.AdminService_API.repository;

import org.researchbucks.AdminService_API.model.SurveyQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyQuestionRepository extends MongoRepository<SurveyQuestion, String> {

    List<SurveyQuestion> getSurveyQuestionsBySurveyId(Long surveyId);
}

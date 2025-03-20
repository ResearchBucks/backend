package org.researchbucks.repository;

import org.researchbucks.dto.SurveyAnswersDto;
import org.researchbucks.model.SurveyData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyDataRepository extends MongoRepository<SurveyData, Long> {

    SurveyAnswersDto findBySurveyIdAndUserId(Long surveyId, Long userId);

    void deleteBySurveyIdAndUserId(Long surveyId, Long userId);
}

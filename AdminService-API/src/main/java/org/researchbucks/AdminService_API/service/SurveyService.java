package org.researchbucks.AdminService_API.service;

import org.researchbucks.AdminService_API.dto.ResponseDto;

public interface SurveyService {

    ResponseDto getAllSurveys();

    ResponseDto getSurveyQuestionsById(Long surveyId);

    ResponseDto approveSurvey(Long surveyId);
}

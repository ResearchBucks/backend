package org.researchbucks.ResearcherService_API.service;

import org.researchbucks.ResearcherService_API.dto.ResponseDto;
import org.researchbucks.ResearcherService_API.dto.SurveyDto;
import org.researchbucks.ResearcherService_API.model.PaymentStatus;

public interface SurveyService {

    ResponseDto createSurvey(Long researcherId, SurveyDto surveyDto);

    ResponseDto getAllSurveys(Long researcherId);

    ResponseDto getSurveyQuestions(Long surveyId);

    ResponseDto updateSurveyDetails(Long surveyId, SurveyDto surveyDto);

    ResponseDto updatePaymentStatus(Long surveyId, PaymentStatus paymentStatus);

    ResponseDto deleteSurvey(Long surveyId);
}

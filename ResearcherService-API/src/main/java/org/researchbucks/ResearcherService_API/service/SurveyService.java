package org.researchbucks.ResearcherService_API.service;

import org.researchbucks.ResearcherService_API.dto.PaymentUpdateDto;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;
import org.researchbucks.ResearcherService_API.dto.SurveyDto;

public interface SurveyService {

    ResponseDto createSurvey(Long researcherId, SurveyDto surveyDto);

    ResponseDto getAllSurveys(Long researcherId);

    ResponseDto getSurveyQuestions(Long surveyId);

    ResponseDto updateSurveyDetails(Long surveyId, SurveyDto surveyDto);

    ResponseDto updatePaymentStatus(Long surveyId, PaymentUpdateDto paymentUpdateDto);

    ResponseDto deleteSurvey(Long surveyId);

    ResponseDto getSurveyResponses(Long surveyId);
}

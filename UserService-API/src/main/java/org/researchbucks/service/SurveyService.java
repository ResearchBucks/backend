package org.researchbucks.service;

import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.SurveyAnswersDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SurveyService {
    ResponseDto getAllAvailableSurveys(Long userId);

    ResponseDto storeSurveyAnswers(Long surveyId, Long userId, List<SurveyAnswersDto> surveyAnswersDto);

    ResponseDto deleteResponse(Long surveyId, Long userId);
}

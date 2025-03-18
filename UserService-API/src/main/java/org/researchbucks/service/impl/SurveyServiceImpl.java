package org.researchbucks.service.impl;

import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.SurveyAnswersDto;
import org.researchbucks.service.SurveyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Override
    public ResponseDto getAllAvailableSurveys(Long userId) {
        //Todo: call the ML model and retrieve available surveys to the relevant user
        return null;
    }

    @Override
    public ResponseDto storeSurveyAnswers(Long surveyId, Long userId, List<SurveyAnswersDto> surveyAnswersDto) {
        //ToDo: store data in the document database
        return null;
    }

    @Override
    public ResponseDto deleteResponse(Long surveyId, Long userId) {
        //ToDo: delete a response from the document database
        return null;
    }
}

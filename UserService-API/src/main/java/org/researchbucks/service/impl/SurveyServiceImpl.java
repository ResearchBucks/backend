package org.researchbucks.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.SurveyAnswersDto;
import org.researchbucks.model.Respondent;
import org.researchbucks.model.UserSurvey;
import org.researchbucks.model.SurveyData;
import org.researchbucks.repository.SurveyDataRepository;
import org.researchbucks.repository.SurveyRepository;
import org.researchbucks.repository.UserRepository;
import org.researchbucks.service.SurveyService;
import org.researchbucks.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyDataRepository surveyDataRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseDto getAllAvailableSurveys(Long userId) {
        //Todo: call the ML model and retrieve available surveys to the relevant user
        return null;
    }

    @Override
    public ResponseDto storeSurveyAnswers(Long surveyId, Long userId, List<SurveyAnswersDto> surveyAnswersDto) {
        try {
            UserSurvey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted()) throw new Exception(CommonMessages.SURVEY_DELETED);
            if(surveyDataRepository.existsBySurveyIdAndUserId(surveyId, userId)) throw new Exception(CommonMessages.SURVEY_ALREADY_ANSWERED);
            Respondent respondent = userRepository.findById(userId).get();
            survey.addRespondent(respondent);
            surveyRepository.save(survey);
            surveyDataRepository.save(SurveyData.builder()
                    .surveyId(surveyId)
                    .answers(surveyAnswersDto.stream().collect(Collectors.toMap(SurveyAnswersDto::getQuestionId, SurveyAnswersDto::getAnswer)))
                    .userId(userId)
                    .build());
            log.info(CommonMessages.SURVEY_RESPONSE_SUCCESS);
            return ResponseDto.builder()
                    .message(CommonMessages.SURVEY_RESPONSE_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteResponse(Long surveyId, Long userId) {
        try{
            SurveyAnswersDto surveyAnswersDto = surveyDataRepository.findBySurveyIdAndUserId(surveyId, userId);
            if(surveyAnswersDto != null){
                surveyDataRepository.deleteBySurveyIdAndUserId(surveyId, userId);
                return ResponseDto.builder()
                        .message(CommonMessages.RESPONSE_DELETED)
                        .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                        .build();
            } else {
                return ResponseDto.builder()
                        .message(CommonMessages.RESPONSE_NOT_FOUND)
                        .status(CommonMessages.RESPONSE_DTO_FAILED)
                        .build();
            }
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }

    }
}

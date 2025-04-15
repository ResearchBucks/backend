package org.researchbucks.ResearcherService_API.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;
import org.researchbucks.ResearcherService_API.dto.SurveyDto;
import org.researchbucks.ResearcherService_API.dto.SurveyQuestionDto;
import org.researchbucks.ResearcherService_API.model.Researcher;
import org.researchbucks.ResearcherService_API.model.Survey;
import org.researchbucks.ResearcherService_API.model.SurveyQuestion;
import org.researchbucks.ResearcherService_API.repository.ResearcherRepository;
import org.researchbucks.ResearcherService_API.repository.SurveyQuestionRepository;
import org.researchbucks.ResearcherService_API.repository.SurveyRepository;
import org.researchbucks.ResearcherService_API.service.SurveyService;
import org.researchbucks.ResearcherService_API.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private ResearcherRepository researcherRepository;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;

    @Override
    public ResponseDto createSurvey(Long researcherId, SurveyDto surveyDto) {
        try{
            Researcher researcher = researcherRepository.findById(researcherId).get();
            if (researcher.getIsDeleted()) throw new Exception(CommonMessages.INVALID_RESEARCHER);
            Survey survey = new Survey().builder()
                    .title(surveyDto.getTitle())
                    .createdDate(new Date())
                    .expiringDate(surveyDto.getExpiringDate())
                    .isDeleted(false)
                    .paymentStatus(surveyDto.getPaymentStatus())
                    .isVerified(false)
                    .researcher(researcher)
                    .paymentPerUser(surveyDto.getPaymentPerUser())
                    .build();
            surveyRepository.save(survey);
            log.info(CommonMessages.SURVEY_SAVED);
            surveyQuestionRepository.save(SurveyQuestion.builder()
                    .surveyId(survey.getId())
                    .researcherId(researcherId)
                    .questions(surveyDto.getSurveyQuestionList().stream().collect(Collectors.toMap(SurveyQuestionDto::getQuestionId, SurveyQuestionDto::getQuestion)))
                    .build());
            log.info(CommonMessages.SURVEY_Q_SAVED);
            return ResponseDto.builder()
                    .message(CommonMessages.SURVEY_SAVED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(survey.getId())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }

    }

    @Override
    public ResponseDto getAllSurveys(Long researcherId) {
        try{
            log.info(CommonMessages.GET_SURVEY);
            ResponseDto responseDto = new ResponseDto<>().builder()
                    .message(CommonMessages.GET_SURVEY_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(surveyRepository.getAllByResearcherIdAndIsDeletedFalse(researcherId))
                    .build();
            log.info(CommonMessages.GET_SURVEY_SUCCESS);
            return responseDto;
        }catch (Exception e){
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto getSurveyQuestions(Long surveyId) {
        try{
            log.info(CommonMessages.GET_SURVEY_Q);
            ResponseDto responseDto = new ResponseDto<>().builder()
                    .message(CommonMessages.GET_SURVEY_Q_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(surveyQuestionRepository.getAllBySurveyId(surveyId))
                    .build();
            log.info(CommonMessages.GET_SURVEY_Q_SUCCESS);
            return responseDto;
        } catch (Exception e) {
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }
}

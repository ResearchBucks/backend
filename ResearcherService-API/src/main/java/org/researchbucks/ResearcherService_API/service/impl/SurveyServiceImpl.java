package org.researchbucks.ResearcherService_API.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.ResearcherService_API.dto.PaymentUpdateDto;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;
import org.researchbucks.ResearcherService_API.dto.SurveyDto;
import org.researchbucks.ResearcherService_API.dto.SurveyQuestionDto;
import org.researchbucks.ResearcherService_API.enums.PaymentStatus;
import org.researchbucks.ResearcherService_API.model.Researcher;
import org.researchbucks.ResearcherService_API.model.Survey;
import org.researchbucks.ResearcherService_API.model.SurveyQuestion;
import org.researchbucks.ResearcherService_API.model.UserSurvey;
import org.researchbucks.ResearcherService_API.repository.*;
import org.researchbucks.ResearcherService_API.service.SurveyService;
import org.researchbucks.ResearcherService_API.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
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
    @Autowired
    private SurveyDataRepository surveyDataRepository;
    @Autowired
    private UserSurveyRepository userSurveyRepository;

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
                    .surveyPrice(surveyDto.getSurveyPrice())
                    .description(surveyDto.getDescription())
                    .isRejected(false)
                    .build();
            if(surveyDto.getPaymentStatus().equals(PaymentStatus.FAILED)){
                survey.setRemainingAmountToPay(survey.getSurveyPrice());
            }else {
                survey.setRemainingAmountToPay(0);
                survey.setPaidDate(new Date());
            }
            surveyRepository.save(survey);
            UserSurvey userSurvey = new UserSurvey().builder()
                            .id(survey.getId())
                            .expiredAt(survey.getExpiringDate())
                            .isDeleted(survey.getIsDeleted())
                            .isPaidToRespondents(false)
                            .isVerified(false)
                            .title(survey.getTitle())
                            .description(survey.getDescription())
                            .paymentPerUser(survey.getPaymentPerUser())
                            .isRejected(false)
                            .build();
            userSurveyRepository.save(userSurvey);
            log.info(CommonMessages.SURVEY_SAVED);
            surveyQuestionRepository.save(SurveyQuestion.builder()
                    .surveyId(survey.getId())
                    .researcherId(researcherId)
                    .questions(surveyDto.getSurveyQuestionList().stream().collect(Collectors.toMap(SurveyQuestionDto::getQuestionId, SurveyQuestionDto::getQuestionDetails)))
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
            log.error(e.getMessage());
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
            Survey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted()) throw new Exception(CommonMessages.INVALID_SURVEY);
            ResponseDto responseDto = new ResponseDto<>().builder()
                    .message(CommonMessages.GET_SURVEY_Q_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(surveyQuestionRepository.getAllBySurveyId(surveyId))
                    .build();
            log.info(CommonMessages.GET_SURVEY_Q_SUCCESS);
            return responseDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto updateSurveyDetails(Long surveyId, SurveyDto surveyDto) {
        try{
            log.info(CommonMessages.UPDATE_SURVEY);
            Survey survey = surveyRepository.findById(surveyId).get();
            UserSurvey userSurvey = userSurveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted() || !survey.getIsVerified()) throw new Exception(CommonMessages.INVALID_SURVEY);
            if(surveyDto.getTitle() != null) {survey.setTitle(surveyDto.getTitle()); userSurvey.setTitle(surveyDto.getTitle());}
            if(surveyDto.getDescription() != null) {survey.setDescription(surveyDto.getDescription()); userSurvey.setDescription(surveyDto.getDescription());}
            if(surveyDto.getPaymentPerUser() != null) {survey.setPaymentPerUser(surveyDto.getPaymentPerUser()); userSurvey.setPaymentPerUser(surveyDto.getPaymentPerUser());}
            if(surveyDto.getExpiringDate() != null) {survey.setExpiringDate(surveyDto.getExpiringDate()); userSurvey.setExpiredAt(surveyDto.getExpiringDate());}
            if(surveyDto.getDescription() != null) {survey.setDescription(surveyDto.getDescription()); userSurvey.setDescription(surveyDto.getDescription());}
            survey.setLastEditedDate(new Date());
            survey.setIsVerified(false);
            userSurvey.setIsVerified(false);
            surveyRepository.save(survey);
            userSurveyRepository.save(userSurvey);
            if(surveyDto.getSurveyQuestionList() != null){
                SurveyQuestion surveyQuestion = surveyQuestionRepository.getBySurveyId(surveyId);
                surveyQuestion.setQuestions(surveyDto.getSurveyQuestionList().stream().collect(Collectors.toMap(SurveyQuestionDto::getQuestionId, SurveyQuestionDto::getQuestionDetails)));
                surveyQuestionRepository.save(surveyQuestion);
            }
            log.info(CommonMessages.SURVEY_UPDATED);
            return ResponseDto.builder()
                    .message(CommonMessages.SURVEY_UPDATED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
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
    public ResponseDto updatePaymentStatus(Long surveyId, PaymentUpdateDto paymentUpdateDto) {
        try {
            log.info(CommonMessages.UPDATE_PAYMENT);
            Survey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted()) throw new Exception(CommonMessages.INVALID_SURVEY);
            if(survey.getPaymentStatus().equals(PaymentStatus.FAILED) && paymentUpdateDto.getPaymentStatus().equals(PaymentStatus.COMPLETED)){
                survey.setRemainingAmountToPay(0);
                survey.setPaidDate(new Date());
            }
            survey.setPaymentStatus(paymentUpdateDto.getPaymentStatus());
            surveyRepository.save(survey);
            log.info(CommonMessages.PAYMENT_UPDATED);
            return ResponseDto.builder()
                    .message(CommonMessages.PAYMENT_UPDATED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
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
    public ResponseDto deleteSurvey(Long surveyId) {
        try{
            log.info(CommonMessages.GET_SURVEY);
            Survey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted()) throw new Exception(CommonMessages.INVALID_SURVEY);
            survey.setIsDeleted(true);
            surveyRepository.save(survey);
            surveyQuestionRepository.deleteBySurveyId(surveyId);
            userSurveyRepository.updateIsDeleted(surveyId, true);
            log.info(CommonMessages.SURVEY_DELETE);
            return ResponseDto.builder()
                    .message(CommonMessages.SURVEY_DELETE)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(survey)
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
    public ResponseDto getSurveyResponses(Long surveyId) {
        try{
            log.info(CommonMessages.GET_SURVEY_A);
            Survey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted() || !survey.getIsVerified()) throw new Exception(CommonMessages.INVALID_SURVEY);
            ResponseDto responseDto = new ResponseDto<>().builder()
                    .message(CommonMessages.GET_SURVEY_A_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(surveyDataRepository.getAllBySurveyId(surveyId))
                    .build();
            log.info(CommonMessages.GET_SURVEY_A_SUCCESS);
            return  responseDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            return  ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }

    }
}

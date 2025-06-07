package org.researchbucks.AdminService_API.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.AdminService_API.dto.EmailParamDto;
import org.researchbucks.AdminService_API.dto.ResponseDto;
import org.researchbucks.AdminService_API.enums.PaymentStatus;
import org.researchbucks.AdminService_API.model.Survey;
import org.researchbucks.AdminService_API.model.UserSurvey;
import org.researchbucks.AdminService_API.repository.SurveyQuestionRepository;
import org.researchbucks.AdminService_API.repository.SurveyRepository;
import org.researchbucks.AdminService_API.repository.UserSurveyRepository;
import org.researchbucks.AdminService_API.service.EmailService;
import org.researchbucks.AdminService_API.service.SurveyService;
import org.researchbucks.AdminService_API.util.CommonMessages;
import org.researchbucks.AdminService_API.util.EmailCreateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserSurveyRepository userSurveyRepository;

    @Override
    public ResponseDto getAllSurveys() {
        try{
            log.info(CommonMessages.GET_SURVEY);
            ResponseDto responseDto = new ResponseDto<>().builder()
                    .message(CommonMessages.GET_SURVEY_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(surveyRepository.getAllByIsDeletedIsFalse())
                    .build();
            log.info(CommonMessages.GET_SURVEY_SUCCESS);
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
    public ResponseDto getSurveyQuestionsById(Long surveyId) {
        try{
            log.info(CommonMessages.GET_SURVEY_Q);
            Survey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted()) throw new Exception(CommonMessages.INVALID_SURVEY);
            ResponseDto responseDto = new ResponseDto<>().builder()
                    .message(CommonMessages.GET_SURVEY_Q_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(surveyQuestionRepository.getSurveyQuestionsBySurveyId(surveyId))
                    .build();
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
    public ResponseDto approveSurvey(Long surveyId) {
        try{
            log.info(CommonMessages.GET_SURVEY);
            Survey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted()) throw new Exception(CommonMessages.INVALID_SURVEY);
            if(survey.getIsVerified()) throw new Exception(CommonMessages.APPROVED_SURVEY);
            surveyRepository.approveSurvey(true, new Date(),surveyId);
            surveyRepository.rejectSurvey(false, surveyId);
            userSurveyRepository.approveSurvey(true, surveyId);
            userSurveyRepository.rejectSurvey(false, surveyId);
            log.info(CommonMessages.SURVEY_APPROVED);
            EmailParamDto emailParamDto = EmailCreateUtil.createSurveyApprovalEmail(survey.getResearcher().getFirstName(),
                    CommonMessages.EMAIL_B_APPROVE, CommonMessages.EMAIL_SUB_APPROVE);
            emailService.sendEmail(survey.getResearcher().getEmail(), emailParamDto);
            return ResponseDto.builder()
                    .message(CommonMessages.SURVEY_APPROVED)
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
    public ResponseDto approvePaymentRefund(Long surveyId) {
        try{
            log.info(CommonMessages.GET_SURVEY);
            Survey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted()) throw new Exception(CommonMessages.INVALID_SURVEY);
            if(!(survey.getPaymentStatus() == PaymentStatus.COMPLETED)) throw new Exception(CommonMessages.SURVEY_NOT_PAID);
            surveyRepository.updatePaymentStatus(PaymentStatus.REFUNDED, surveyId);
            log.info(CommonMessages.REFUNDED);
            EmailParamDto emailParamDto = EmailCreateUtil.createRefundEmail(survey.getResearcher().getFirstName());
            emailService.sendEmail(survey.getResearcher().getEmail(), emailParamDto);
            return ResponseDto.builder()
                    .message(CommonMessages.REFUNDED)
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
    public ResponseDto notifySurveyRejection(Long surveyId) {
        try{
            log.info(CommonMessages.GET_SURVEY);
            Survey survey = surveyRepository.findById(surveyId).get();
            if(survey.getIsDeleted()) throw new Exception(CommonMessages.INVALID_SURVEY);
            survey.setIsRejected(true);
            surveyRepository.save(survey);
            UserSurvey userSurvey = userSurveyRepository.findById(surveyId).get();
            userSurvey.setIsRejected(true);
            userSurveyRepository.save(userSurvey);
            EmailParamDto emailParamDto = EmailCreateUtil.createSurveyApprovalEmail(survey.getResearcher().getFirstName(),
                    CommonMessages.EMAIL_B_REJECT, CommonMessages.EMAIL_SUB_REJECT);
            emailService.sendEmail(survey.getResearcher().getEmail(), emailParamDto);
            log.info(CommonMessages.NOTIFIED_R);
            return ResponseDto.builder()
                    .message(CommonMessages.NOTIFIED_R)
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
}

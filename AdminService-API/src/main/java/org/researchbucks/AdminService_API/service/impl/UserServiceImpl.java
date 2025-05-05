package org.researchbucks.AdminService_API.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.AdminService_API.dto.ResponseDto;
import org.researchbucks.AdminService_API.model.Researcher;
import org.researchbucks.AdminService_API.model.Respondent;
import org.researchbucks.AdminService_API.model.UserSurvey;
import org.researchbucks.AdminService_API.repository.ResearcherRepository;
import org.researchbucks.AdminService_API.repository.RespondentRepository;
import org.researchbucks.AdminService_API.repository.SurveyRepository;
import org.researchbucks.AdminService_API.repository.UserSurveyRepository;
import org.researchbucks.AdminService_API.service.UserService;
import org.researchbucks.AdminService_API.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RespondentRepository respondentRepository;
    @Autowired
    private ResearcherRepository researcherRepository;
    @Autowired
    private UserSurveyRepository userSurveyRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public ResponseDto lockRespondent(Long respondentId) {
        try{
            log.info(CommonMessages.GET_USER);
            Respondent respondent = respondentRepository.findById(respondentId).get();
            if(respondent.getIsDeleted() || respondent.getIsLocked()) throw new Exception(CommonMessages.INVALID_USER);
            respondentRepository.updateLockStatus(true, respondentId);
            log.info(CommonMessages.USER_LOCKED);
            return ResponseDto.builder()
                    .message(CommonMessages.USER_LOCKED)
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
    public ResponseDto unlockRespondent(Long respondentId) {
        try {
            log.info(CommonMessages.GET_USER);
            Respondent respondent = respondentRepository.findById(respondentId).get();
            if(respondent.getIsDeleted()) throw new Exception(CommonMessages.INVALID_USER);
            respondentRepository.updateLockStatus(false, respondentId);
            log.info(CommonMessages.USER_UNLOCKED);
            return ResponseDto.builder()
                    .message(CommonMessages.USER_UNLOCKED)
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
    public ResponseDto lockResearcher(Long researcherId) {
        try {
            log.info(CommonMessages.GET_USER);
            Researcher researcher = researcherRepository.findById(researcherId).get();
            if(researcher.getIsDeleted() || researcher.getIsLocked()) throw new Exception(CommonMessages.INVALID_USER);
            researcherRepository.updateLockStatus(true, researcherId);
            log.info(CommonMessages.USER_LOCKED);
            return ResponseDto.builder()
                    .message(CommonMessages.USER_LOCKED)
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
    public ResponseDto unlockResearcher(Long researcherId) {
        try{
            log.info(CommonMessages.GET_USER);
            Researcher researcher = researcherRepository.findById(researcherId).get();
            if(researcher.getIsDeleted()) throw new Exception(CommonMessages.INVALID_USER);
            researcherRepository.updateLockStatus(false, researcherId);
            log.info(CommonMessages.USER_UNLOCKED);
            return ResponseDto.builder()
                    .message(CommonMessages.USER_UNLOCKED)
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
    public ResponseDto getAllResearchers() {
        try {
            log.info(CommonMessages.GET_USER);
            ResponseDto responseDto = new ResponseDto<>().builder()
                    .message(CommonMessages.GET_USER_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(researcherRepository.findAll())
                    .build();
            log.info(CommonMessages.GET_USER_SUCCESS);
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
    public ResponseDto getAllRespondents() {
        try{
            log.info(CommonMessages.GET_USER);
            ResponseDto responseDto = new ResponseDto<>().builder()
                    .message(CommonMessages.GET_USER_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(respondentRepository.findAll())
                    .build();
            log.info(CommonMessages.GET_USER_SUCCESS);
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
    public ResponseDto updateRespondentEarnings(Long surveyId) {
        try{
            log.info(CommonMessages.GET_SURVEY);
            UserSurvey userSurvey = userSurveyRepository.findById(surveyId).get();
            if(userSurvey.getIsDeleted()) throw new Exception(CommonMessages.INVALID_SURVEY);
            if(userSurvey.getIsPaidToRespondents()) throw new Exception(CommonMessages.EARNIGS_ALREADY_UPDATED);
            List<Respondent> respondents = userSurvey.getRespondents();
            Integer paymentPerUser = surveyRepository.findPaymentPerUserById(surveyId);
            respondents.forEach(
                    respondent -> {
                        respondent.setTotalEarnings(respondent.getTotalEarnings()+paymentPerUser);
                    }
            );
            respondentRepository.saveAll(respondents);
            userSurvey.setIsPaidToRespondents(true);
            userSurveyRepository.save(userSurvey);
            log.info(CommonMessages.EARNINGS_UPDATED);
            return ResponseDto.builder()
                    .message(CommonMessages.EARNINGS_UPDATED)
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
}

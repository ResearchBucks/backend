package org.researchbucks.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.dto.EmailParamDto;
import org.researchbucks.dto.RespondentUpdateDto;
import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.UserRegDto;
import org.researchbucks.model.Respondent;
import org.researchbucks.enums.Role;
import org.researchbucks.repository.UserRepository;
import org.researchbucks.service.EmailService;
import org.researchbucks.service.UserService;
import org.researchbucks.util.CommonMessages;
import org.researchbucks.util.EmailCreateUtil;
import org.researchbucks.util.SecurityUtil;
import org.researchbucks.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${respondent.defaultEarning}")
    private Integer defaultEarning;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseDto registerRespondent(UserRegDto userRegDto){
        try{
            Date date = new Date();
            String verifyToken = jwtUtil.generateVerificationTokenFromUserName(userRegDto.getEmail());
            Respondent respondent = Respondent.builder()
                    .firstName(userRegDto.getFirstName())
                    .lastName(userRegDto.getLastName())
                    .email(userRegDto.getEmail())
                    .mobile(userRegDto.getMobile())
                    .nic(userRegDto.getNic())
                    .address(userRegDto.getAddress())
                    .createdDate(date)
                    .isVerified(false)
                    .totalEarnings(defaultEarning)
                    .isDeleted(false)
                    .isLocked(false)
                    .role(Role.ROLE_RESPONDENT)
                    .verificationToken(SecurityUtil.hashToken(verifyToken))
                    .build();
            userRepository.save(respondent);
            EmailParamDto emailParamDto = EmailCreateUtil.createVerificationEmail(userRegDto.getFirstName(), verifyToken);
            emailService.sendEmail(userRegDto.getEmail(), emailParamDto);
            log.info(CommonMessages.RESPONDENT_SAVED_SUCCESSFULLY);
            return ResponseDto.builder().
                    message(CommonMessages.RESPONDENT_SAVED_SUCCESSFULLY).
                    status(CommonMessages.RESPONSE_DTO_SUCCESS).
                    build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return  ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto getAllRespondents() {
        try {
            log.info(CommonMessages.GET_ALL_RESPONDENTS);
            ResponseDto responseDto = ResponseDto.builder()
                    .message(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(userRepository.findAll())
                    .build();
            log.info(CommonMessages.GET_RESPONDENT_SUCCESS);
            return responseDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            return  ResponseDto.builder()
                    .message(e.getLocalizedMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto getRespondentById(Long id) {
        try {
            log.info(CommonMessages.GET_RESPONDENT);
            Respondent respondent = userRepository.findById(id).get();
            if(respondent.getIsDeleted()) throw new Exception(CommonMessages.INVALID_RESPONDENT);
            ResponseDto responseDto = ResponseDto.builder()
                    .message(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(userRepository.findById(id))
                    .build();
            log.info(CommonMessages.GET_RESPONDENT_SUCCESS);
            return responseDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            return  ResponseDto.builder()
                    .message(CommonMessages.INVALID_RESPONDENT)
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto updateRespondent(Long id, RespondentUpdateDto respondentUpdateDto) {
        try {
            log.info(CommonMessages.GET_RESPONDENT);
            Respondent respondent = userRepository.findById(id).get();
            if(respondent.getIsDeleted()) throw new Exception(CommonMessages.INVALID_RESPONDENT);
            if(respondentUpdateDto.getMobile() != null) respondent.setMobile(respondentUpdateDto.getMobile());
            if(respondentUpdateDto.getAddress() != null) respondent.setAddress(respondentUpdateDto.getAddress());
            if(respondentUpdateDto.getPassword() != null) respondent.setPassword(SecurityUtil.hashPassword(respondentUpdateDto.getPassword()));
            userRepository.save(respondent);
            log.info(CommonMessages.RESPONDENT_UPDATED);
            return ResponseDto.builder()
                    .message(CommonMessages.RESPONDENT_UPDATED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(respondent)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return  ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteRespondent(Long id) {
        try {
            log.info(CommonMessages.GET_RESPONDENT);
            Respondent respondent = userRepository.findById(id).get();
            if(respondent.getIsDeleted()) throw new Exception(CommonMessages.INVALID_RESPONDENT);
            respondent.setIsDeleted(true);
            userRepository.save(respondent);
            log.info(CommonMessages.RESPONDENT_DELETED);
            return ResponseDto.builder()
                    .message(CommonMessages.RESPONDENT_DELETED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(respondent)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return  ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto requestPasswordReset(String email) {
        try{
            log.info(CommonMessages.GET_RESPONDENT);
            Respondent respondent = userRepository.findByEmail(email);
            if(respondent.getIsDeleted() || !respondent.getIsVerified()) throw new Exception(CommonMessages.INVALID_RESPONDENT);
            String token = jwtUtil.generateResetTokenFromUserName(email);
            respondent.setResetToken(SecurityUtil.hashToken(token));
            userRepository.save(respondent);
            EmailParamDto emailParamDto = EmailCreateUtil.createResetPasswordEmail(respondent.getFirstName(), token);
            emailService.sendEmail(email, emailParamDto);
            return ResponseDto.builder()
                    .message(CommonMessages.RESET_M_SENT)
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

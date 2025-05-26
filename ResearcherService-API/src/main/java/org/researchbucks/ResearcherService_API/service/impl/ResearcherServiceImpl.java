package org.researchbucks.ResearcherService_API.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.ResearcherService_API.dto.EmailParamDto;
import org.researchbucks.ResearcherService_API.dto.ResearcherRegDto;
import org.researchbucks.ResearcherService_API.dto.ResearcherUpdateDto;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;
import org.researchbucks.ResearcherService_API.enums.Role;
import org.researchbucks.ResearcherService_API.model.Researcher;
import org.researchbucks.ResearcherService_API.repository.ResearcherRepository;
import org.researchbucks.ResearcherService_API.service.EmailService;
import org.researchbucks.ResearcherService_API.service.ResearcherService;
import org.researchbucks.ResearcherService_API.util.CommonMessages;
import org.researchbucks.ResearcherService_API.util.EmailCreateUtil;
import org.researchbucks.ResearcherService_API.util.SecurityUtil;
import org.researchbucks.ResearcherService_API.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ResearcherServiceImpl implements ResearcherService {

    @Autowired
    private ResearcherRepository researcherRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseDto registerResearcher(ResearcherRegDto researcherRegDto) {
        try{
            Date date = new Date();
            String verifyToken = jwtUtil.generateVerificationTokenFromUserName(researcherRegDto.getEmail());
            Researcher researcher = Researcher.builder()
                    .firstName(researcherRegDto.getFirstName())
                    .lastName(researcherRegDto.getLastName())
                    .occupation(researcherRegDto.getOccupation())
                    .email(researcherRegDto.getEmail())
                    .mobile(researcherRegDto.getMobile())
                    .nic(researcherRegDto.getNic())
                    .address(researcherRegDto.getAddress())
                    .createdDate(date)
                    .isVerified(false)
                    .isDeleted(false)
                    .isLocked(false)
                    .role(Role.ROLE_RESEARCHER)
                    .verificationToken(SecurityUtil.hashToken(verifyToken))
                    .build();
            researcherRepository.save(researcher);
            //ToDo: create verification url
            EmailParamDto emailParamDto = EmailCreateUtil.createVerificationEmail(researcherRegDto.getFirstName(), verifyToken);
            emailService.sendEmail(researcherRegDto.getEmail(), emailParamDto);
            log.info(CommonMessages.RESEARCHER_SAVED);
            return ResponseDto.builder().
                    message(CommonMessages.RESEARCHER_SAVED).
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
    public ResponseDto getAllResearchers() {
        try {
            log.info(CommonMessages.GET_ALL_RESEARCHERS);
            ResponseDto responseDto = ResponseDto.builder()
                    .message(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(researcherRepository.findAll())
                    .build();
            log.info(CommonMessages.GET_RESEARCHER_SUCCESS);
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
    public ResponseDto getResearcherById(Long id) {
        try {
            log.info(CommonMessages.GET_RESEARCHER);
            Researcher researcher = researcherRepository.findById(id).get();
            if(researcher.getIsDeleted()) throw new Exception(CommonMessages.INVALID_RESEARCHER);
            ResponseDto responseDto = ResponseDto.builder()
                    .message(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(researcher)
                    .build();
            log.info(CommonMessages.GET_RESEARCHER_SUCCESS);
            return responseDto;
        } catch (Exception e) {
            log.error(e.getMessage());
            return  ResponseDto.builder()
                    .message(CommonMessages.INVALID_RESEARCHER)
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }

    @Override
    public ResponseDto updateResearcher(Long id, ResearcherUpdateDto researcherUpdateDto) {
        try {
            log.info(CommonMessages.GET_RESEARCHER);
            Researcher researcher = researcherRepository.findById(id).get();
            if(researcher.getIsDeleted() || !researcher.getIsVerified()) throw new Exception(CommonMessages.INVALID_RESEARCHER);
            if(researcherUpdateDto.getMobile() != null) researcher.setMobile(researcherUpdateDto.getMobile());
            if(researcherUpdateDto.getAddress() != null) researcher.setAddress(researcherUpdateDto.getAddress());
            if(researcherUpdateDto.getPassword() != null) researcher.setPassword(SecurityUtil.hashPassword(researcherUpdateDto.getPassword()));
            if(researcherUpdateDto.getOccupation() != null) researcher.setOccupation(researcherUpdateDto.getOccupation());
            researcherRepository.save(researcher);
            log.info(CommonMessages.RESEARCHER_UPDATED);
            return ResponseDto.builder()
                    .message(CommonMessages.RESEARCHER_UPDATED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(researcher)
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
    public ResponseDto deleteResearcher(Long id) {
        try {
            log.info(CommonMessages.GET_RESEARCHER);
            Researcher researcher = researcherRepository.findById(id).get();
            if(researcher.getIsDeleted() || !researcher.getIsVerified()) throw new Exception(CommonMessages.INVALID_RESEARCHER);
            researcher.setIsDeleted(true);
            researcherRepository.save(researcher);
            log.info(CommonMessages.RESEARCHER_DELETED);
            return ResponseDto.builder()
                    .message(CommonMessages.RESEARCHER_DELETED)
                    .status(CommonMessages.RESPONSE_DTO_SUCCESS)
                    .data(researcher)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return  ResponseDto.builder()
                    .message(e.getMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }
}

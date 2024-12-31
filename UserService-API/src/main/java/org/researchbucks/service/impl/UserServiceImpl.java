package org.researchbucks.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.UserRegDto;
import org.researchbucks.model.Respondent;
import org.researchbucks.repository.UserRepository;
import org.researchbucks.service.UserService;
import org.researchbucks.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseDto registerRespondent(UserRegDto userRegDto){
        try{
            Date date = new Date();
            Respondent respondent = Respondent.builder()
                    .firstName(userRegDto.getFirstName())
                    .lastName(userRegDto.getLastName())
                    .email(userRegDto.getEmail())
                    .mobile(userRegDto.getMobile())
                    .nic(userRegDto.getNic())
                    .address(userRegDto.getAddress())
                    .createdDate(date)
                    .password(userRegDto.getPassword())
                    .build();
            userRepository.save(respondent);
            log.info(CommonMessages.RESPONDENT_SAVED_SUCCESSFULLY);
            return ResponseDto.builder().
                    message(CommonMessages.RESPONDENT_SAVED_SUCCESSFULLY).
                    status(CommonMessages.RESPONSE_DTO_SUCCESS).
                    build();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return  ResponseDto.builder()
                    .message(e.getLocalizedMessage())
                    .status(CommonMessages.RESPONSE_DTO_FAILED)
                    .build();
        }
    }
}

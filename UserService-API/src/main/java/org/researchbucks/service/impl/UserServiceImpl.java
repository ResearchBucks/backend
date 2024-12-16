package org.researchbucks.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.dto.UserRegDto;
import org.researchbucks.model.Respondent;
import org.researchbucks.repository.UserRepository;
import org.researchbucks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerRespondent(UserRegDto userRegDto){
        Date date = new Date();
        Respondent respondent = Respondent.builder()
                .firstName(userRegDto.getFirstName())
                .lastName(userRegDto.getLastName())
                .email(userRegDto.getEmail())
                .mobile(userRegDto.getMobile())
                .nic(userRegDto.getNic())
                .address(userRegDto.getAddress())
                .createdDate(date)
                .build();
        userRepository.save(respondent);
        log.info("Respondent saved successfully");
    }
}

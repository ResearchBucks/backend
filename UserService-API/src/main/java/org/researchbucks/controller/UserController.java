package org.researchbucks.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.UserRegDto;
import org.researchbucks.repository.UserRepository;
import org.researchbucks.service.UserService;
import org.researchbucks.util.CommonMessages;
import org.researchbucks.util.InputValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respondent")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerRespondent(@RequestBody UserRegDto userRegDto){
        if(userRepository.existsRespondentByEmail(userRegDto.getEmail()) || userRepository.existsRespondentByNic(userRegDto.getNic())){
            log.error(CommonMessages.RESPONDENT_ALREADY_EXISTS);
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.RESPONDENT_ALREADY_EXISTS)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .data(userRegDto)
                            .build()
            );
        } else if (!InputValidationUtil.isValidNic(userRegDto.getNic())) {
            log.error(CommonMessages.INVALID_NIC);
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.INVALID_NIC)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .data(userRegDto)
                            .build()
            );
        } else {
            return ResponseEntity.ok().body(
                    userService.registerRespondent(userRegDto)
            );
        }
    }

}

package org.researchbucks.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.dto.RespondentUpdateDto;
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
@RequestMapping("/respondent/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    /************************
     Register a respondent
     Return type: ResponseEntity
     ************************/
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
            return ResponseEntity.created(null).body(
                    userService.registerRespondent(userRegDto)
            );
        }
    }

    /************************
     Retrieve all respondents
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getAllRespondents")
    public ResponseEntity<ResponseDto> getAllRespondents(){
        return ResponseEntity.ok().body(
                userService.getAllRespondents()
        );
    }

    /************************
     Retrieve a specific respondent using ID
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getRespondent/{id}")
    public ResponseEntity<ResponseDto> getRespondentById(@PathVariable Long id){
        return ResponseEntity.ok().body(
                userService.getRespondentById(id)
        );
    }

    /************************
     Update respondent details
     Return type: ResponseEntity
     ************************/
    @PutMapping("/updateRespondent/{id}")
    public ResponseEntity<ResponseDto> updateRespondent(@PathVariable Long id, @RequestBody RespondentUpdateDto respondentUpdateDto){
        return ResponseEntity.ok().body(
                userService.updateRespondent(id, respondentUpdateDto)
        );
    }

    /************************
     Delete a respondent
     Return type: ResponseEntity
     ************************/
    @DeleteMapping("/deleteRespondent/{id}")
    public ResponseEntity<ResponseDto> deleteRespondent(@PathVariable Long id){
        return ResponseEntity.ok().body(
                userService.deleteRespondent(id)
        );
    }

    /************************
     Resend verification email
     Return type: ResponseEntity
     ************************/
    @PostMapping("/resendVerifyEmail/{email}")
    public ResponseEntity<ResponseDto> resendVerifyEmail(@PathVariable String email){
        if(userRepository.existsRespondentByEmail(email)){
            return ResponseEntity.ok().body(
                    userService.resendVerifyEmail(email)
            );
        }else {
            log.error(CommonMessages.INVALID_EMAIL);
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.INVALID_EMAIL)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .build()
            );
        }
    }
}

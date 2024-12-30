package org.researchbucks.controller;

import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.UserRegDto;
import org.researchbucks.repository.UserRepository;
import org.researchbucks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respondent")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseDto registerRespondent(@RequestBody UserRegDto userRegDto){
        if(!userRepository.existsRespondentByEmail(userRegDto.getEmail())){
            userService.registerRespondent(userRegDto);
            return new ResponseDto(HttpStatus.CREATED, userRegDto);
        }else {

            return new ResponseDto(HttpStatus.BAD_REQUEST, "User Already Exists", userRegDto);
        }
    }

}

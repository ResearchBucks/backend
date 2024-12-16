package org.researchbucks.controller;

import org.researchbucks.dto.UserRegDto;
import org.researchbucks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respondent")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerRespondent(@RequestBody UserRegDto userRegDto){
        userService.registerRespondent(userRegDto);
    }

}

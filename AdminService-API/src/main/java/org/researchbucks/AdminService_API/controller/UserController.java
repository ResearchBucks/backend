package org.researchbucks.AdminService_API.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.AdminService_API.dto.ResponseDto;
import org.researchbucks.AdminService_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /************************
     Lock respondent account if they violated terms
     Return type: ResponseEntity
     ************************/
    @PostMapping("/lockRespondent/{respondentId}")
    public ResponseEntity<ResponseDto> lockRespondent(@PathVariable Long respondentId){
        return ResponseEntity.ok().body(
          userService.lockRespondent(respondentId)
        );
    }

    /************************
     Unlock respondent account
     Return type: ResponseEntity
     ************************/
    @PostMapping("/unlockRespondent/{respondentId}")
    public ResponseEntity<ResponseDto> unlockRespondent(@PathVariable Long respondentId){
        return ResponseEntity.ok().body(
                userService.unlockRespondent(respondentId)
        );
    }

    /************************
     Lock researcher account if they violated terms
     Return type: ResponseEntity
     ************************/
    @PostMapping("/lockResearcher/{researcherId}")
    public ResponseEntity<ResponseDto> lockResearcher(@PathVariable Long researcherId){
        return ResponseEntity.ok().body(
                userService.lockResearcher(researcherId)
        );
    }

    /************************
     Unlock researcher account
     Return type: ResponseEntity
     ************************/
    @PostMapping("/unlockResearcher/{researcherId}")
    public ResponseEntity<ResponseDto> unlockResearcher(@PathVariable Long researcherId){
        return ResponseEntity.ok().body(
                userService.unlockResearcher(researcherId)
        );
    }
}

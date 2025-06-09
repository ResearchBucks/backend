package org.researchbucks.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.dto.ResponseDto;
import org.researchbucks.dto.SurveyAnswersDto;
import org.researchbucks.service.SurveyService;
import org.researchbucks.service.UserService;
import org.researchbucks.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respondent/survey")
@Slf4j
public class SurveyController {

    @Autowired
    private SurveyService surveyService;
    @Autowired
    private UserService userService;

    /************************
     Getting available surveys of the user from the ML model
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getAllSurveys/{userId}")
    public ResponseEntity<ResponseDto> getAllAvailableSurveys(@PathVariable Long userId){
        log.info(CommonMessages.GET_USER_SURVEY);
        return ResponseEntity.ok().body(
                surveyService.getAllAvailableSurveys(userId)
        );
    }

    /************************
     Store survey answers that answered by users
     Return type: ResponseEntity
     ************************/
    @PostMapping("/saveAnswers/{surveyId}/{userId}")
    public ResponseEntity<ResponseDto> storeSurveyAnswers(
            @PathVariable Long surveyId,
            @PathVariable Long userId,
            @RequestBody List<SurveyAnswersDto> surveyAnswersDto){
        log.info(CommonMessages.STORE_SURVEY_DATA);
        return ResponseEntity.ok().body(
                surveyService.storeSurveyAnswers(surveyId, userId, surveyAnswersDto)
        );
    }

    /************************
     Delete a response that provided by a respondent
     Return type: ResponseEntity
     ************************/
    @DeleteMapping("/deleteResponse/{surveyId}/{userId}")
    public ResponseEntity<ResponseDto> deleteResponse(@PathVariable Long surveyId, @PathVariable Long userId){
        log.info(CommonMessages.CHECK_RESPONSE);
        return ResponseEntity.ok().body(
          surveyService.deleteResponse(surveyId, userId)
        );
    }
}

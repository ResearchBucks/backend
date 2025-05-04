package org.researchbucks.AdminService_API.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.AdminService_API.dto.ResponseDto;
import org.researchbucks.AdminService_API.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@Slf4j
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    /************************
     Get all survey details
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getAllSurveys")
    public ResponseEntity<ResponseDto> getAllSurveys(){
        return ResponseEntity.ok().body(
              surveyService.getAllSurveys()
        );
    }

    /************************
     Get all survey questions using surveyId
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getSurveyQuestions/{surveyId}")
    public ResponseEntity<ResponseDto> getSurveyQuestionsById(@PathVariable Long surveyId){
        return ResponseEntity.ok().body(
                surveyService.getSurveyQuestionsById(surveyId)
        );
    }

    /************************
     Approve a survey
     Return type: ResponseEntity
     ************************/
    @PostMapping("/approveSurvey/{surveyId}")
    public ResponseEntity<ResponseDto> approveSurvey(@PathVariable Long surveyId){
        return ResponseEntity.ok().body(
          surveyService.approveSurvey(surveyId)
        );
    }

    /************************
     Update payment status after refund approval
     Return type: ResponseEntity
     ************************/
    @PostMapping("/approvePaymentRefund/{surveyId}")
    public ResponseEntity<ResponseDto> approvePaymentRefund(@PathVariable Long surveyId){
        return ResponseEntity.ok().body(
          surveyService.approvePaymentRefund(surveyId)
        );
    }

    /************************
     Notify researcher via email about survey rejection
     Return type: ResponseEntity
     ************************/
    @PostMapping("/notifySurveyRejection/{surveyId}")
    public ResponseEntity<ResponseDto> notifySurveyRejection(@PathVariable Long surveyId){
        return ResponseEntity.ok().body(
          surveyService.notifySurveyRejection(surveyId)
        );
    }
}

package org.researchbucks.ResearcherService_API.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;
import org.researchbucks.ResearcherService_API.dto.SurveyDto;
import org.researchbucks.ResearcherService_API.model.PaymentStatus;
import org.researchbucks.ResearcherService_API.service.SurveyService;
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
     Create a survey
     Return type: ResponseEntity
     ************************/
    @PostMapping("/create/{researcherId}")
    public ResponseEntity<ResponseDto> createSurvey(@PathVariable Long researcherId, @RequestBody SurveyDto surveyDto){
        return ResponseEntity.created(null).body(
                surveyService.createSurvey(researcherId, surveyDto)
        );
    }

    /************************
     Get all surveys that researcher created
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getAllSurveys/{researcherId}")
    public ResponseEntity<ResponseDto> getAllSurveys(@PathVariable Long researcherId){
        return ResponseEntity.ok().body(
                surveyService.getAllSurveys(researcherId)
        );
    }

    /************************
     Get survey questions that included in a specific survey
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getSurveyQuestions/{surveyId}")
    public ResponseEntity<ResponseDto> getSurveyQuestions(@PathVariable Long surveyId){
        return ResponseEntity.ok().body(
                surveyService.getSurveyQuestions(surveyId)
        );
    }

    /************************
     Update survey details and questions
     Return type: ResponseEntity
     ************************/
    @PutMapping("/updateSurveyDetails/{surveyId}")
    public ResponseEntity<ResponseDto> updateSurveyDetails(@PathVariable Long surveyId, @RequestBody SurveyDto surveyDto){
        return ResponseEntity.ok().body(
                surveyService.updateSurveyDetails(surveyId, surveyDto)
        );
    }

    /************************
     Update payment status of a survey
     Return type: ResponseEntity
     ************************/
    @PutMapping("/updatePaymentStatus/{surveyId}")
    public ResponseEntity<ResponseDto> updatePaymentStatus(@PathVariable Long surveyId, @RequestBody PaymentStatus paymentStatus){
        return ResponseEntity.ok().body(
                surveyService.updatePaymentStatus(surveyId, paymentStatus)
        );
    }

    /************************
     Delete a survey
     Return type: ResponseEntity
     ************************/
    @DeleteMapping("/deleteSurvey/{surveyId}")
    public ResponseEntity<ResponseDto> deleteSurvey(@PathVariable Long surveyId){
        return ResponseEntity.ok().body(
                surveyService.deleteSurvey(surveyId)
        );
    }
}

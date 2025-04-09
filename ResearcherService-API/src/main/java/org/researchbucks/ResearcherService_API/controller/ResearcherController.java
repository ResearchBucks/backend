package org.researchbucks.ResearcherService_API.controller;

import lombok.extern.slf4j.Slf4j;
import org.researchbucks.ResearcherService_API.dto.ResearcherRegDto;
import org.researchbucks.ResearcherService_API.dto.ResponseDto;
import org.researchbucks.ResearcherService_API.repository.ResearcherRepository;
import org.researchbucks.ResearcherService_API.service.ResearcherService;
import org.researchbucks.ResearcherService_API.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/researcher")
@Slf4j
public class ResearcherController {

    @Autowired
    private ResearcherRepository researcherRepository;
    @Autowired
    private ResearcherService researcherService;

    /************************
     Register a researcher
     Return type: ResponseEntity
     ************************/
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerResearcher(@RequestBody ResearcherRegDto researcherRegDto){
        if(researcherRepository.existsResearcherByEmail(researcherRegDto.getEmail()) || researcherRepository.existsResearcherByNic(researcherRegDto.getNic())){
            log.error(CommonMessages.RESEARCHER_EXISTS);
            return ResponseEntity.badRequest().body(
                    ResponseDto.builder()
                            .message(CommonMessages.RESEARCHER_EXISTS)
                            .status(CommonMessages.RESPONSE_DTO_FAILED)
                            .data(researcherRegDto)
                            .build()
            );
        }else {
            return ResponseEntity.created(null).body(
                    researcherService.registerResearcher(researcherRegDto)
            );
        }
    }

    /************************
     Verify researcher after email verification
     Return type: ResponseEntity
     ************************/
    @PostMapping("/verifyResearcher")
    public ResponseEntity<ResponseDto> verifyResearcher(@RequestBody ResearcherRegDto researcherRegDto){
        return ResponseEntity.ok().body(
                researcherService.verifyResearcher(researcherRegDto)
        );
    }

    /************************
     Retrieve all researchers
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getAllResearchers")
    public ResponseEntity<ResponseDto> getAllResearchers(){
        return ResponseEntity.ok().body(
                researcherService.getAllRespondents()
        );
    }

    /************************
     Retrieve a specific researcher using ID
     Return type: ResponseEntity
     ************************/
    @GetMapping("/getRespondent/{id}")
    public ResponseEntity<ResponseDto> getRespondentById(@PathVariable Long id){
        return ResponseEntity.ok().body(
                researcherService.getResearcherById(id)
        );
    }
}

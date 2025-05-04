package org.researchbucks.AdminService_API.service;

import org.researchbucks.AdminService_API.dto.ResponseDto;

public interface UserService {
    
    ResponseDto lockRespondent(Long respondentId);

    ResponseDto unlockRespondent(Long respondentId);

    ResponseDto lockResearcher(Long researcherId);

    ResponseDto unlockResearcher(Long researcherId);

    ResponseDto getAllResearchers();

    ResponseDto getAllRespondents();
}

package org.researchbucks.ResearcherService_API.service;

import org.researchbucks.ResearcherService_API.dto.EmailParamDto;

public interface EmailService {

    void sendEmail(String to, EmailParamDto emailParamDto);
}

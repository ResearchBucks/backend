package org.researchbucks.AdminService_API.service;

import org.researchbucks.AdminService_API.dto.EmailParamDto;

public interface EmailService {

    void sendEmail(String to, EmailParamDto emailParamDto);
}

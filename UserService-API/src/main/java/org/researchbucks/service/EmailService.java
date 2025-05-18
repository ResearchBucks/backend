package org.researchbucks.service;

import org.researchbucks.dto.EmailParamDto;

public interface EmailService {

    void sendEmail(String to, EmailParamDto emailParamDto);
}

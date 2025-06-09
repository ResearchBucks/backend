package org.researchbucks.AdminService_API.service.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.researchbucks.AdminService_API.dto.EmailParamDto;
import org.researchbucks.AdminService_API.service.EmailService;
import org.researchbucks.AdminService_API.util.CommonMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${logo.path}")
    private String logoUrl;

    @Async
    @Override
    public void sendEmail(String to, EmailParamDto emailParamDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(emailParamDto.getSub());
            Context context = new Context();
            context.setVariables(emailParamDto.getProperties());
            String html = templateEngine.process(emailParamDto.getHtmlTemplate(), context);
            helper.setText(html, true);
            ClassPathResource res = new ClassPathResource(logoUrl);
            helper.addInline("logoImage", res);
            javaMailSender.send(message);
            log.info(CommonMessages.MAIL_QUEUED);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

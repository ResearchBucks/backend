package org.researchbucks.ResearcherService_API.util;

import org.researchbucks.ResearcherService_API.dto.EmailParamDto;
import org.researchbucks.ResearcherService_API.enums.Role;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmailCreateUtil {

    public static EmailParamDto createVerificationEmail(String username, String token){
        String path = "/signupPassword";
        String verificationUrl = generateUrl(path, token);
        Map<String, Object> properties = new HashMap<>();
        properties.put(CommonMessages.USERNAME, username);
        properties.put(CommonMessages.VERIFY_URL, verificationUrl);
        properties.put(CommonMessages.YEAR, LocalDate.now().getYear());
        return new EmailParamDto().builder()
                .sub(CommonMessages.EMAIL_SUB_ACTIVATE)
                .properties(properties)
                .htmlTemplate("/accountVerifyEmailTemplate.html")
                .build();
    }

    public static EmailParamDto createResetPasswordEmail(String username, String token){
        String path = "/resetPassword";
        String resetUrl = generateUrl(path, token);
        Map<String, Object> properties = new HashMap<>();
        properties.put(CommonMessages.USERNAME, username);
        properties.put(CommonMessages.VERIFY_URL, resetUrl);
        properties.put(CommonMessages.YEAR, LocalDate.now().getYear());
        return new EmailParamDto().builder()
                .sub(CommonMessages.EMAIL_SUB_RESET)
                .properties(properties)
                .htmlTemplate("/resetPasswordEmailTemplate.html")
                .build();
    }

    private static String generateUrl(String path, String token){
        return UriComponentsBuilder.fromUriString("http://20.121.120.231:80")
                .path(path)
                .queryParam("token", token)
                .queryParam("role", Role.ROLE_RESEARCHER)
                .build()
                .toUriString();
    }
}

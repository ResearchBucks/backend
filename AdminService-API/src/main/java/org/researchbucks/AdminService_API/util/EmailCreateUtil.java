package org.researchbucks.AdminService_API.util;

import org.researchbucks.AdminService_API.dto.EmailParamDto;
import org.researchbucks.AdminService_API.enums.AdminRole;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmailCreateUtil {

    public static EmailParamDto createSurveyApprovalEmail(String username, String msg, String sub){
        Map<String, Object> properties = new HashMap<>();
        properties.put(CommonMessages.USERNAME, username);
        properties.put(CommonMessages.APPROVE_MSG, msg);
        properties.put(CommonMessages.YEAR, LocalDate.now().getYear());
        return new EmailParamDto().builder()
                .sub(sub)
                .properties(properties)
                .htmlTemplate("/surveyApproval.html")
                .build();
    }

    public static EmailParamDto createRefundEmail(String username){
        Map<String, Object> properties = new HashMap<>();
        properties.put(CommonMessages.USERNAME, username);
        properties.put(CommonMessages.YEAR, LocalDate.now().getYear());
        return new EmailParamDto().builder()
                .sub(CommonMessages.EMAIL_SUB_REFUND)
                .properties(properties)
                .htmlTemplate("/refundStatus.html")
                .build();
    }

    public static EmailParamDto createResetPasswordEmail(String username, String token, AdminRole role){
        String path = "/admin/auth/resetPassword";
        String resetUrl = generateUrl(path, token, role);
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

    private static String generateUrl(String path, String token, AdminRole role){
        return UriComponentsBuilder.fromUriString("http://20.121.120.231:80")
                .path(path)
                .queryParam("token", token)
                .queryParam("role", role)
                .build()
                .toUriString();
    }
}

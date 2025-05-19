package org.researchbucks.AdminService_API.util;

import org.researchbucks.AdminService_API.dto.EmailParamDto;

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
}

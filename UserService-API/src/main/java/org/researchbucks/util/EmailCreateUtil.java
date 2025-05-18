package org.researchbucks.util;

import org.researchbucks.dto.EmailParamDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmailCreateUtil {

    public static EmailParamDto createVerificationEmail(String username, String verificationUrl){
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
}

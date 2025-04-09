package org.researchbucks.ResearcherService_API.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SecurityUtil {

    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}

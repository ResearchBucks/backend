package org.researchbucks.util;

public class InputValidationUtil {

    public static boolean isValidNic(String nic){
        return nic.matches("^[0-9]{9}[vV]$") || nic.matches("^[0-9]{7}[0][0-9]{4}$");
    }
}

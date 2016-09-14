package com.startemplan.vyuspot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Class which has Utility methods
 * 
 */
public class Utility {
    private static Pattern pattern;
    private static Matcher matcher;
    //Email Pattern
    private static final String EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
    /**
     * Validate Email with regular expression
     * 
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */
    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isNotNull(String txt){
        return txt!=null && txt.trim().length()>0 ? true: false;
    }

    public static boolean numvalid(String num){
        return num.trim().length() < 10 ? false: true;
    }
}



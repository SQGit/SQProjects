package com.emergencysavior.emergencysavior;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RSA on 29-03-2016.
 */
public class CONFIG {

    static final String URL="http://api.emergencysavior.com/webapi/emx";
   static final String GPS_URL_LOCATION="https://www.google.com/maps?q=loc:";
   static final String MULTIPLE_IMAGE="http://api.emergencysavior.com/webapi/emx";
    public static boolean isStringNullOrWhiteSpace(String value) {
        if (value == null) {
            return true;
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    static final String TWITTER_KEY = "Wbz4ZWXE5Km1A5tHLeZ5Y9IWk";
    static final String TWITTER_SECRET = "KGRNnOXrap2Mq8fgG5Gn5JTG0vcakAEzr5NhGhoVzQtQYuTsQk";

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}

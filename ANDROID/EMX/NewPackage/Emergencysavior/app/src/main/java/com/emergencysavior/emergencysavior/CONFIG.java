package com.emergencysavior.emergencysavior;

/**
 * Created by RSA on 29-03-2016.
 */
public class CONFIG {

    static final String URL="http://api.emergencysavior.com/webapi/emx";
    //static final String URL="http://emergencysavior.com/webapi/emx";
   // static final String URL="http://emx.sqindia.net/webapi/emx";
    static final String GPS_URL_LOCATION="https://www.google.com/maps?q=loc:";
   // static final String MULTIPLE_IMAGE="http://emx.sqindia.net/webapi/emx";

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
}

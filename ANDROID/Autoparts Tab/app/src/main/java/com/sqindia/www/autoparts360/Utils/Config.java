package com.sqindia.www.autoparts360.Utils;

/**
 * Created by Salman on 22-03-2017.
 */
public class Config {



    public static String currentPath="http://104.197.80.225/autoparts360/api/";
    public static String localpath="http://192.168.1.9/autoparts360/api/";

    public static final String WEB_URL_LOGIN = currentPath+"login_auth";
    public static final String GET_CAR_PARTS = currentPath+"get_car_list";
    public static final String GET_CAR_FULL_DETAILS=currentPath+"get_car_full_details";
    public static final String DEL_PARTS="http://104.197.80.225/autoparts360/api/delete_car";





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
}

package ru.flymer.flymerclient.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artsiom on 1/13/2015.
 */
public class CookieUtil {

    public static Map<String, String> parseCookieString(String cookie){
        Map<String, String> c = new HashMap<>();
        for(String str : cookie.split(";")){
            String[] cook = str.split("=");
            c.put(cook[0].trim(), cook[1]);
        }
        return c;
    }
}

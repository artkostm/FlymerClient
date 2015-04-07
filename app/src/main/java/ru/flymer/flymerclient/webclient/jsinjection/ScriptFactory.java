package ru.flymer.flymerclient.webclient.jsinjection;

/**
 * Created by Artsiom on 1/13/2015.
 */
public class ScriptFactory implements JavaScriptInjection {

    public static String getLoginImitationScript(String email, String password){
        bodyScript
                .append("document.getElementById('l-email').value = '").append(email).append("';")
                .append("document.getElementById('l-pass').value = '").append(password).append("';")
                .append("document.getElementById('l-submit').click();")
        ;
        workingScript.append(START_SCRIPT)
                .append(bodyScript)
                .append(END_SCRIPT);
        return workingScript.toString();
    }
}

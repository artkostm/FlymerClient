package ru.flymer.flymerclient.webclient.jsinjection;

/**
 * Created by Artsiom on 1/12/2015.
 */
public interface JavaScriptInjection {

    public static StringBuilder workingScript = new StringBuilder();

    public static final String START_SCRIPT = "javascript:(function() { ";

    public static final String END_SCRIPT = "})()";

    public static final String HIDE_ELEMENT_BY_ID = "function hide(id){if (document.getElementById(id)){document.getElementById(id).style['display'] = 'none';}}";

    public static final String HIDE_ELEMENTS_BY_CLASS_NAME = "function hideByClass(c){var e=document.getElementsByClassName(c);for(var i=0;i<e.length;i++){e[i].style['display'] = 'none';}}";


    public static StringBuilder bodyScript = new StringBuilder();
//            .append("hide('header');")
//            .append("hide('footer');")
//            .append("hide('col-b');")
//            .append("hide('clearb');")
//            .append("hide('oauth');")
//            .append("document.getElementById('l-submit').onclick = function(){boobs.showBigTits('4D');};")
//            .append("hide('login-options');")
//            .append("document.getElementById('oauth-or').style['visibility'] = 'hidden';")
//            .append( "hideByClass('gotoflyner');");

}

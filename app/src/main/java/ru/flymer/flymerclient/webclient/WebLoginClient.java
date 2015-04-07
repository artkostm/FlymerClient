package ru.flymer.flymerclient.webclient;

import android.graphics.Bitmap;
import android.os.Message;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Map;

import ru.flymer.flymerclient.activities.BaseWebActivity;
import ru.flymer.flymerclient.util.CookieUtil;
import ru.flymer.flymerclient.webclient.jsinjection.ScriptFactory;

/**
 * Created by Artsiom on 1/11/2015.
 */
public class WebLoginClient extends WebViewClient{

    private String email;
    private String password;
    private boolean success;

    public WebLoginClient(String email, String password){
        this.email = email;
        this.password = password;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        Toast.makeText(view.getContext(), "ARGS: "+args, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        view.loadUrl(ScriptFactory.getLoginImitationScript(email, password));

        String cs = CookieManager.getInstance().getCookie("http://flymer.ru/");
        Map<String, String> cookies = CookieUtil.parseCookieString(cs);

        if(cookies.containsKey("ac") && cookies.containsKey("fkey")){
            BaseWebActivity base = (BaseWebActivity)view.getContext();
            base.saveCookies(cookies, cs, new String[]{email, password});
        }
//        Toast.makeText(view.getContext(), cs, Toast.LENGTH_LONG).show();
    }
}
package ru.flymer.flymerclient.webclient;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Artsiom on 1/12/2015.
 */
public class LoginWebView extends WebView{

    public LoginWebView(Context context) {
        super(context);
    }

    public LoginWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void postUrl(String url, byte[] postData) {
        Toast.makeText(getContext(), "Size: "+postData.length, Toast.LENGTH_LONG).show();
        super.postUrl(url, postData);
    }
}
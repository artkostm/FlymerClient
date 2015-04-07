package ru.flymer.flymerclient.httpclient;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Artsiom on 2/21/2015.
 */
public class FlymerHttpClient {

    public static interface MethodListener<T> {

        public void sendResult(T result);

        public void sendError(int code, String msg);

    }

    private static AsyncHttpClient client;

    public static void get(MethodListener listener){}

    public static void post(MethodListener listener){}
}

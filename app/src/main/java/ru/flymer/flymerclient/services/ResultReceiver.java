package ru.flymer.flymerclient.services;

import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Artsiom on 1/20/2015.
 */
public class ResultReceiver extends android.os.ResultReceiver {

    private Receiver receiver;

    public interface Receiver{
        public void onReceiveResult(int code, Bundle data);
    }

    public ResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if(receiver != null){
            receiver.onReceiveResult(resultCode, resultData);
        }
    }
}

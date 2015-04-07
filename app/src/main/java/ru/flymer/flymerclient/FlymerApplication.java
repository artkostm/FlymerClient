package ru.flymer.flymerclient;

import android.app.Application;
import android.content.Context;

/**
 * Created by Artsiom on 2/19/2015.
 */
public class FlymerApplication extends Application {

    private static FlymerApplication instance;



    private FlymerApplication(){
        super();
    }

    public static FlymerApplication getInstance(){
        if(instance == null){
            instance = new FlymerApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

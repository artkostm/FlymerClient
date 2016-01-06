package ru.flymer.flymerclient.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.Calendar;

import ru.flymer.flymerclient.services.NewReplyCheckerService;

/**
 * Created by Artsiom on 1/22/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private AlarmManager alarmMgr;

    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NewReplyCheckerService.class);
        context.startService(service);
    }
}

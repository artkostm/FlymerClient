package ru.flymer.flymerclient.activities;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;

import java.util.Map;

import ru.flymer.flymerclient.provider.DatabaseMetadata;
import ru.flymer.flymerclient.receivers.AlarmReceiver;

/**
 * Created by Artsiom on 1/21/2015.
 */
public abstract class BaseWebActivity extends ActionBarActivity {

    private AlarmReceiver alarm = new AlarmReceiver();

    final Uri USER_URI = Uri
            .parse("content://ru.flymer.flymerclient.UserList/user");

    private Map<String, String> cookies;

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void saveCookies(Map<String, String> cookies, String cookie, String... credentials) {
//        getContentResolver().delete(USER_URI, null, null);
        ContentValues cv = new ContentValues();
        cv.put(DatabaseMetadata.USER_COOKIE, cookie);
        cv.put(DatabaseMetadata.USER_AC, cookies.get(DatabaseMetadata.USER_AC));
        cv.put(DatabaseMetadata.USER_FKEY, cookies.get(DatabaseMetadata.USER_FKEY));
        cv.put(DatabaseMetadata.USER_SID, cookies.get(DatabaseMetadata.USER_SID));
        cv.put(DatabaseMetadata.USER_EMAIL, credentials[0]);
        cv.put(DatabaseMetadata.USER_PASSWORD, credentials[1]);
        Uri newUri = getContentResolver().insert(USER_URI, cv);
        this.cookies = cookies;
        alarm.setAlarm(this);
        this.finish();
    }

    public boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        }
        return cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    protected void onDestroy() {
//        alarm.cancelAlarm(this);
        super.onDestroy();
    }
}

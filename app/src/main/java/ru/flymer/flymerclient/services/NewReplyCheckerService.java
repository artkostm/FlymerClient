package ru.flymer.flymerclient.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.IBinder;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import ru.flymer.flymerclient.R;
import ru.flymer.flymerclient.provider.DatabaseMetadata;

public class NewReplyCheckerService extends Service {

    private AsyncHttpClient client;
    private RequestHandle rh;

    private String cookie;

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    final Uri USER_URI = Uri
            .parse("content://ru.flymer.flymerclient.UserList/user");

    public NewReplyCheckerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        client = new AsyncHttpClient();
        Cursor cursor = getContentResolver().query(USER_URI, null, null,
                null, null);
        cursor.moveToFirst();
        cookie = cursor.getString(cursor.getColumnIndex(DatabaseMetadata.USER_COOKIE));
        cursor.close();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        if(!isOnline()){
            return START_STICKY;
        }

        Map<String, String> params = new HashMap<>();
        params.put("c", "1");
        params.put("ts", String.valueOf(new Date().getTime()));

        rh = client.get(this, "http://flymer.ru/req/repcount", new Header[]{
                new BasicHeader("Accept", "*/*"),
                new BasicHeader("Host", "flymer.ru"),
                new BasicHeader("Connection", "keep-alive"),
                new BasicHeader("Cookie", cookie)
        }, new RequestParams(params), responseHandler);
        return START_STICKY;
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

    private void sendNotification(int num, String link){
        Notification notif = new Notification(R.drawable.flymer_notif_icon, "Новые ответы!",
                System.currentTimeMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        notif.setLatestEventInfo(this, "Flymer", "У тебя есть новые ответы", pIntent);
        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.number = num;
//        notif.defaults |= Notification.DEFAULT_SOUND;
//        notif.defaults |= Notification.DEFAULT_LIGHTS;
//        notif.defaults |= Notification.DEFAULT_VIBRATE;

        mNotificationManager.notify(NOTIFICATION_ID, notif);
    }

    private String processLink(String link){
        return link.replace("\\", "");
    }

    private JsonHttpResponseHandler responseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            if (statusCode == 200) {
                try {
                    JSONObject replies = response.getJSONObject("replies");
                    Integer num = replies.getInt("num");
                    String url = null;
                    if (num > 0) {
                        url = replies.getString("url");
                        sendNotification(num, processLink(url));
                    }else{
                        mNotificationManager.cancel(NOTIFICATION_ID);
                    }
                    replies = null;
                    response = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    client.cancelAllRequests(true);
//                    NewReplyCheckerService.this.stopSelf();
                    if(rh != null){
                        rh.shouldBeGarbageCollected();
                    }
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        client.cancelAllRequests(true);
        super.onDestroy();
    }
}



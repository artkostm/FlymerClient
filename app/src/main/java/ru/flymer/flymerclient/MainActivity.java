package ru.flymer.flymerclient;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ru.flymer.flymerclient.activities.BaseWebActivity;
import ru.flymer.flymerclient.provider.DatabaseMetadata;


public class MainActivity extends BaseWebActivity {

    final Uri USER_URI = Uri
            .parse("content://ru.flymer.flymerclient.UserList/user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cursor cursor = getContentResolver().query(USER_URI, null, null,
                null, null);
        cursor.moveToFirst();
        String cookie = cursor.getString(cursor.getColumnIndex(DatabaseMetadata.USER_COOKIE));
        cursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

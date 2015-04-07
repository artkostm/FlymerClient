package ru.flymer.flymerclient.webclient.jsinjection;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import ru.flymer.flymerclient.MainActivity;

/**
 * Created by Artsiom on 1/12/2015.
 */
public class ScriptCallback {

    private Context context;

    public ScriptCallback(Context c){
        context = c;
    }

    @JavascriptInterface
    public void showBigTits(String size){
        Toast.makeText(context, "You can see "+size+" size boobs!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}

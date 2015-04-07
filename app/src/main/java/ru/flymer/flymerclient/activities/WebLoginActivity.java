package ru.flymer.flymerclient.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.flymer.flymerclient.R;
import ru.flymer.flymerclient.webclient.LoginWebView;
import ru.flymer.flymerclient.webclient.WebLoginClient;

public class WebLoginActivity extends BaseWebActivity {

    private WebView web;
    private EditText email;
    private EditText pass;
    private Button loginBtn;
    private WebLoginClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);

        web = new LoginWebView(this);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        loginBtn = (Button)findViewById(R.id.login);

        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(false);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isOnline()){
                    Toast.makeText(WebLoginActivity.this, "Please, use some network connection",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()){
                    if(client != null){
                        client.setEmail(email.getText().toString());
                        client.setPassword(pass.getText().toString());
                    }else{
                        client = new WebLoginClient(email.getText().toString(),
                                pass.getText().toString());
                        web.setWebViewClient(client);
                    }
                    web.loadUrl("http://flymer.ru/");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        web.clearHistory();
        web.clearCache(true);
        web.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_login, menu);
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

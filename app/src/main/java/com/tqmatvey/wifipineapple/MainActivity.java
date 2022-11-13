package com.tqmatvey.wifipineapple;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public boolean isConnectedToPineapple() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 172.16.42.1");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (java.io.IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WebViewActivity.class);
                if(isConnectedToPineapple()) {
                    view.getContext().startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Please connect to Wifi Pineapple AP", Toast.LENGTH_LONG).show();//display the text of button2
                }
            }
        });
    }
}
package com.tqmatvey.wifipineapple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity {
    public Boolean isConnectedToPineapple()
    {
        final AtomicBoolean b = new AtomicBoolean(false);
         Thread thread = new Thread(new Runnable() {    // if we remove this line, ANR error
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process ipProcess = runtime.exec("/system/bin/ping -c 1 172.16.42.1");
                    int     exitValue = ipProcess.waitFor();
                    b.set(exitValue == 0);
                } catch (java.io.IOException e)          { e.printStackTrace(); }
                catch (InterruptedException e) { e.printStackTrace(); }
                b.set(false);
            }
        });

        thread.start(); // launching thread
        //try { thread.join(); } catch (InterruptedException e) { e.printStackTrace(); } // wait for thread to complete
        return b.get();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        Switch forceConnectSwitch = (Switch) findViewById(R.id.switch1);
        //CircularProgressIndicator loading = (CircularProgressIndicator) findViewById(R.id.loading);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WebViewActivity.class);
                Boolean forceConnectSwitchState = forceConnectSwitch.isChecked();
                // loading.setVisibility(View.VISIBLE);
                if(forceConnectSwitchState){
                    view.getContext().startActivity(intent);
                } else if(isConnectedToPineapple()) {
                    view.getContext().startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Please connect to Wifi Pineapple AP", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
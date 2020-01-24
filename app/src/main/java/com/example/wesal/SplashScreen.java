package com.example.wesal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2500);
                    Intent intent=new Intent(getApplicationContext(),Home_Activity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void testUpload(){
        int i=1+1;
        int ii=i+1;
        int iii=ii+1;
    }
}

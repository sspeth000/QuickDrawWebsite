package com.draw.rkb.drawsimply;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/* Decompiled from the supplied classes.dex_Decompiler.com(1).zip. */
public class SplashScreenActivity extends AppCompatActivity {
    private int SPLASH_TIME = 2;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash_screen);
        new LogoLaunch().start();
    }

    private class LogoLaunch extends Thread {
        private LogoLaunch() { }

        @Override
        public void run() {
            try {
                sleep(SplashScreenActivity.this.SPLASH_TIME * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            SplashScreenActivity.this.finish();
        }
    }
}
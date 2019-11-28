package com.hayya5k.simanja.Splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.hayya5k.simanja.Dashboard.MainActivity;
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Login.LoginActivity;
import com.hayya5k.simanja.R;
import com.pixplicity.easyprefs.library.Prefs;

public class SplashActivity extends AppCompatActivity {

    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        ctx = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Prefs.getBoolean(Constant.isLogin,false)) {
                    Log.d("SPLASH", "run: " + "SPLASH");
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();

                }else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }

            }}, 3000);


    }

    @Override
    public void onBackPressed() {

    }
}
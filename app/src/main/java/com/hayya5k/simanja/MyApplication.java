package com.hayya5k.simanja;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.pixplicity.easyprefs.library.Prefs;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(this);

        Prefs.initPrefs(this);

    }
}

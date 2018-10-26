package com.githup.bryan.heimadallr.sample;

import android.app.Application;

import com.githup.bryan.heimadallr_android.Heimadallr;

public class DemoAPP  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Heimadallr.install(this).start();
    }
}

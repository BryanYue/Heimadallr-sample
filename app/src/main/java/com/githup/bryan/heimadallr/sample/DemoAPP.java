package com.githup.bryan.heimadallr.sample;

import android.app.Application;

import com.githup.bryan.heimadallr_android.Heimadallr;
import com.squareup.leakcanary.LeakCanary;

public class DemoAPP  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);


        Heimadallr.install(this);
    }

}

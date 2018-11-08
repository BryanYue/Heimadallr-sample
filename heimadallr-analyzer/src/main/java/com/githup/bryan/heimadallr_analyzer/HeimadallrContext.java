package com.githup.bryan.heimadallr_analyzer;

import android.content.Context;

import com.githup.bryan.heimadallr_analyzer.internal.HeimadallrInfo;
import com.tencent.mmkv.MMKV;

public class HeimadallrContext implements HeimadallrInterceptor {

    private static Context sApplicationContext;
    private static HeimadallrContext sInstance = null;


    public static void init(Context context) {
        MMKV.initialize(context);
        sInstance = new HeimadallrContext();
        sApplicationContext = context;

    }


    public static HeimadallrContext get() {
        if (sInstance == null) {
            throw new RuntimeException("HeimadallrContext null");
        } else {
            return sInstance;
        }
    }


    public Context provideContext() {
        return sApplicationContext;
    }

    public String provideQualifier() {
        return "unknown";
    }

    public String provideUid() {
        return "uid";
    }

    public String provideNetworkType() {
        return "unknown";
    }

    public int provideMonitorDuration() {
        return -1;
    }

    public int provideBlockThreshold() {
        return 1000;
    }

    public int provideDumpInterval() {
        return provideBlockThreshold();
    }

    public String providePath() {
        return "/Heimadallr/";
    }


    @Override
    public void onBlock(Context context, HeimadallrInfo blockInfo) {

    }


    public boolean displayNotification() {
        return true;
    }



    public boolean stopWhenDebugging() {
        return true;
    }
}

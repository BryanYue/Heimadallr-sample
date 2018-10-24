package com.githup.bryan.heimadallr;

import android.content.Context;

import com.githup.bryan.heimadallr.internal.HeimadallrInfo;

public class HeimadallrContext  implements  HeimadallrInterceptor{

    private static Context sApplicationContext;
    private static HeimadallrContext sInstance = null;


    public static void init(Context context, HeimadallrContext heimadallrContext) {
        sApplicationContext = context;
        sInstance = heimadallrContext;
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

    public int provideMonitorDuration() {
        return -1;
    }


    @Override
    public void onBlock(Context context, HeimadallrInfo blockInfo) {

    }




    public boolean displayNotification() {
        return true;
    }
}

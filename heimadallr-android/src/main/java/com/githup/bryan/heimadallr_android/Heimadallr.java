package com.githup.bryan.heimadallr_android;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.githup.bryan.heimadallr_analyzer.HeimadallrContext;
import com.githup.bryan.heimadallr_analyzer.HeimadallrInternals;
import com.githup.bryan.heimadallr_android.ui.DisplayActivity;
import com.githup.bryan.heimadallr_android.ui.PermissionActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
import static android.content.pm.PackageManager.DONT_KILL_APP;

public class Heimadallr {
    private static final String TAG = "Heimadallr";

    private static Heimadallr sInstance;
    private HeimadallrInternals mHeimadallrCore;
    private boolean mMonitorStarted = false;



    public Heimadallr() {
        HeimadallrInternals.setContext(HeimadallrContext.get());
        mHeimadallrCore = HeimadallrInternals.getInstance();
        mHeimadallrCore.addBlockInterceptor(HeimadallrContext.get());
        if (!HeimadallrContext.get().displayNotification()) {
            return;
        }
        mHeimadallrCore.addBlockInterceptor(new DisplayService());
    }

    public static void install(Context context) {
        HeimadallrContext.init(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                PermissionActivity.startPermissionActivity(context);
            }else {
                initHeimadallr();
            }
        }else {
            initHeimadallr();
        }


    }

    private static void initHeimadallr() {
        setEnabled(HeimadallrContext.get().provideContext(), DisplayActivity.class, HeimadallrContext.get().displayNotification());
        get().start();
    }


    public static Heimadallr get() {
        if (sInstance == null) {
            synchronized (Heimadallr.class) {
                if (sInstance == null) {
                    sInstance = new Heimadallr();
                    sInstance.start();
                }
            }
        }
        return sInstance;
    }


    public void start() {
        if (!mMonitorStarted) {
            mMonitorStarted = true;
            Looper.getMainLooper().setMessageLogging(mHeimadallrCore.monitor);
        }
    }

    public void stop() {
        if (mMonitorStarted) {
            mMonitorStarted = false;
            Looper.getMainLooper().setMessageLogging(null);
            mHeimadallrCore.stackSampler.stop();
            mHeimadallrCore.cpuSampler.stop();
        }
    }

    public void upload() {

    }


    public void recordStartTime() {
        PreferenceManager.getDefaultSharedPreferences(HeimadallrContext.get().provideContext())
                .edit()
                .putLong("Heimadallr_StartTime", System.currentTimeMillis())
                .commit();
    }


    public boolean isMonitorDurationEnd() {
        long startTime = PreferenceManager.getDefaultSharedPreferences(HeimadallrContext.get().provideContext()).getLong("Heimadallr_StartTime", 0);
        return startTime != 0 && System.currentTimeMillis() - startTime >
                HeimadallrContext.get().provideMonitorDuration() * 3600 * 1000;
    }


    public static void setEnabled(Context context,
                                   final Class<?> componentClass,
                                   final boolean enabled) {
        final Context appContext = context.getApplicationContext();
        executeOnFileIoThread(new Runnable() {
            @Override
            public void run() {
                setEnabledBlocking(appContext, componentClass, enabled);
            }
        });
    }

    private static void executeOnFileIoThread(Runnable runnable) {
        fileIoExecutor.execute(runnable);
    }


    private static final Executor fileIoExecutor = newSingleThreadExecutor("File-IO");


    private static Executor newSingleThreadExecutor(String threadName) {
        return Executors.newSingleThreadExecutor(new SingleThreadFactory(threadName));
    }


    private static void setEnabledBlocking(Context appContext,
                                           Class<?> componentClass,
                                           boolean enabled) {
        ComponentName component = new ComponentName(appContext, componentClass);
        PackageManager packageManager = appContext.getPackageManager();
        int newState = enabled ? COMPONENT_ENABLED_STATE_ENABLED : COMPONENT_ENABLED_STATE_DISABLED;
        // Blocks on IPC.
        packageManager.setComponentEnabledSetting(component, newState, DONT_KILL_APP);
    }


}

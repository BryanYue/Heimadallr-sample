package com.githup.bryan.heimadallr.sample;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.githup.bryan.heimadallr_android.Heimadallr;

public class DemoAPP  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }

//        LeakCanary.install(this);


        Heimadallr.install(this);
        Heimadallr.getTid();
//        GodEye.instance().init(this);
//        if (isMainProcess(this)) {//安装只能在主进程
//            GodEye.instance()
//                    .install(new BatteryConfig(this))
//                    .install(new CpuConfig())
//                    .install(new CrashConfig(new CrashFileProvider(this)))
//                    .install(new FpsConfig(this))
//                    .install(new HeapConfig())
//                    .install(new LeakConfig(this,new RxPermissionRequest()))
//                    .install(new PageloadConfig(this))
//                    .install(new PssConfig(this))
//                    .install(new RamConfig(this))
//                    .install(new SmConfig(this))
//                    .install(new ThreadConfig())
//                    .install(new TrafficConfig());
//            GodEyeMonitor.work(this);
//        }
    }
    /**
     * 是否主进程
     */
    private static boolean isMainProcess(Application application) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) application.getSystemService
                (Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return application.getPackageName().equals(processName);
    }
}

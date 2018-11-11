package com.githup.bryan.heimadallr_analyzer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.githup.bryan.heimadallr_analyzer.internal.HeimadallrInfo;
import com.githup.bryan.heimadallr_analyzer.internal.ProcessUtils;
import com.tencent.mmkv.MMKV;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HeimadallrInternals {


    private static HeimadallrInternals sInstance;
    private static HeimadallrContext sContext;
    private List<HeimadallrInterceptor> mInterceptorChain = new LinkedList<>();

    public LooperMonitor monitor;
    public StackSampler stackSampler;
    public CpuSampler cpuSampler;
    long startTime=0;

    public static HeimadallrInternals getInstance() {
        if (sInstance == null) {
            synchronized (HeimadallrInternals.class) {
                if (sInstance == null) {
                    sInstance = new HeimadallrInternals();
                }
            }
        }
        return sInstance;
    }

    public HeimadallrInternals() {
        stackSampler = new StackSampler(
                Looper.getMainLooper().getThread(),
                sContext.provideDumpInterval());

        cpuSampler = new CpuSampler(sContext.provideDumpInterval());

        setMonitor(new LooperMonitor(getContext().provideBlockThreshold(), getContext().stopWhenDebugging(), new LooperMonitor.HeimadallrListener() {

            @Override
            public void onBlockEvent(long realTimeStart, long realTimeEnd,
                                     long threadTimeStart, long threadTimeEnd) {

                ArrayList<String> threadStackEntries = stackSampler
                        .getThreadStackEntries(realTimeStart, realTimeEnd);
                if (!threadStackEntries.isEmpty()) {


                    HeimadallrInfo heimadallrInfo = HeimadallrInfo.newInstance()
                            .setMainThreadTimeCost(realTimeStart, realTimeEnd, threadTimeStart, threadTimeEnd)
                            .setCpuBusyFlag(cpuSampler.isCpuBusy(realTimeStart, realTimeEnd))
                            .setRecentCpuRate(cpuSampler.getCpuRateInfo())
                            .setThreadStackEntries(threadStackEntries)
                            .flushString();

                     startTime = System.currentTimeMillis();
                    LogWriter.save(heimadallrInfo.toString());

                    Log.e("INTERVALS_LOGWRITER",System.currentTimeMillis()-startTime+"");

                    String path = "";
                    startTime = System.currentTimeMillis();
                    path = "looper" + "-" + LogWriter.FILE_NAME_FORMATTER.format(startTime) + ".log";
//                    MMKV.defaultMMKV().encode(path, heimadallrInfo.toString());
                    MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE,"looper1").encode(path, heimadallrInfo.toString());
                    Log.e("INTERVALS_MMKV",System.currentTimeMillis()-startTime+"");

                    Log.e("INTERVALS_MMKV",MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE,"looper1").allKeys().length+"");


                    if (mInterceptorChain.size() != 0) {
                        for (HeimadallrInterceptor interceptor : mInterceptorChain) {
                            interceptor.onBlock(getContext().provideContext(), heimadallrInfo);
                        }
                    }
                }
            }
        }));

        LogWriter.cleanObsolete();
    }

    public static void setContext(HeimadallrContext context) {
        sContext = context;
    }

    public static HeimadallrContext getContext() {
        return sContext;
    }

    public void addBlockInterceptor(HeimadallrInterceptor heimadallrInterceptor) {
        mInterceptorChain.add(heimadallrInterceptor);
    }

    private void setMonitor(LooperMonitor looperPrinter) {
        monitor = looperPrinter;
    }

    long getSampleDelay() {
        return (long) (HeimadallrInternals.getContext().provideBlockThreshold() * 0.8f);
    }

    static String getPath() {
        String state = Environment.getExternalStorageState();
        String logPath = HeimadallrInternals.getContext()
                == null ? "" : HeimadallrInternals.getContext().providePath();

        if (Environment.MEDIA_MOUNTED.equals(state)
                && Environment.getExternalStorageDirectory().canWrite()) {
            return Environment.getExternalStorageDirectory().getPath() + logPath;
        }
        return Environment.getDataDirectory().getAbsolutePath() + HeimadallrInternals.getContext().providePath();
    }

    static File detectedBlockDirectory() {
        File directory = new File(getPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    public static File[] getLogFiles() {
        File f = detectedBlockDirectory();
        if (f.exists() && f.isDirectory()) {
            return f.listFiles(new BlockLogFileFilter());
        }
        return null;
    }

    private static class BlockLogFileFilter implements FilenameFilter {

        private String TYPE = ".log";

        BlockLogFileFilter() {

        }

        @Override
        public boolean accept(File dir, String filename) {
            return filename.endsWith(TYPE);
        }
    }


}

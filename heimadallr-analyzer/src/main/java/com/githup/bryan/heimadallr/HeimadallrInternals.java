package com.githup.bryan.heimadallr;

import java.util.LinkedList;
import java.util.List;

public class HeimadallrInternals {


    private static HeimadallrInternals sInstance;
    private static HeimadallrContext sContext;
    private List<HeimadallrInterceptor> mInterceptorChain = new LinkedList<>();

    public  LooperMonitor monitor;
    public StackSampler stackSampler;
    public CpuSampler cpuSampler;





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


    public static void setContext(HeimadallrContext context) {
        sContext = context;
    }


    public void addBlockInterceptor(HeimadallrInterceptor  heimadallrInterceptor) {
        mInterceptorChain.add(heimadallrInterceptor);
    }
}

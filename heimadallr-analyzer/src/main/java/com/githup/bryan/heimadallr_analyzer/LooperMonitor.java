package com.githup.bryan.heimadallr_analyzer;

import android.os.Debug;
import android.os.SystemClock;
import android.util.Printer;

public class LooperMonitor implements Printer {
    private static final int DEFAULT_BLOCK_THRESHOLD_MILLIS = 3000;

    private long mHeimadallrThresholdMillis = DEFAULT_BLOCK_THRESHOLD_MILLIS;
    private long mStartTimestamp = 0;
    private long mStartThreadTimestamp = 0;

    private HeimadallrListener mHeimadallrListener = null;

    private boolean mPrintingStarted = false;
    private final boolean mStopWhenDebugging;

    public interface HeimadallrListener {
        void onBlockEvent(long realStartTime,
                          long realTimeEnd,
                          long threadTimeStart,
                          long threadTimeEnd);
    }

    public LooperMonitor(long heimadallrThresholdMillis, boolean stopWhenDebugging,HeimadallrListener heimadallrListener) {
        if (heimadallrListener == null) {
            throw new IllegalArgumentException("HeimadallrListener should not be null.");
        }
        this.mHeimadallrListener = heimadallrListener;
        this.mHeimadallrThresholdMillis = heimadallrThresholdMillis;
        this.mStopWhenDebugging = stopWhenDebugging;
    }

    @Override
    public void println(String x) {
        if (mStopWhenDebugging && Debug.isDebuggerConnected()) {
            return;
        }
        if (!mPrintingStarted) {
            mStartTimestamp = System.currentTimeMillis();
            mStartThreadTimestamp = SystemClock.currentThreadTimeMillis();
            mPrintingStarted = true;
            startDump();
        } else {
            final long endTime = System.currentTimeMillis();
            mPrintingStarted = false;
            if (isBlock(endTime)) {
                notifyBlockEvent(endTime);
            }
            stopDump();
        }
    }



    private void startDump() {
        if (null != HeimadallrInternals.getInstance().stackSampler) {
            HeimadallrInternals.getInstance().stackSampler.start();
        }

        if (null != HeimadallrInternals.getInstance().cpuSampler) {
            HeimadallrInternals.getInstance().cpuSampler.start();
        }
    }


    private void stopDump() {
        if (null != HeimadallrInternals.getInstance().stackSampler) {
            HeimadallrInternals.getInstance().stackSampler.stop();
        }

        if (null != HeimadallrInternals.getInstance().cpuSampler) {
            HeimadallrInternals.getInstance().cpuSampler.stop();
        }
    }

    private boolean isBlock(long endTime) {
        return endTime - mStartTimestamp > mHeimadallrThresholdMillis;
    }

    private void notifyBlockEvent(final long endTime) {
        final long startTime = mStartTimestamp;
        final long startThreadTime = mStartThreadTimestamp;
        final long endThreadTime = SystemClock.currentThreadTimeMillis();

        HandlerThreadFactory.getWriteLogThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                mHeimadallrListener.onBlockEvent(startTime, endTime, startThreadTime, endThreadTime);
            }
        });
    }
}

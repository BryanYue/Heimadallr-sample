package com.githup.bryan.heimadallr;

import java.util.concurrent.ThreadFactory;

public class SingleThreadFactory  implements ThreadFactory {
    private final String threadName;

    SingleThreadFactory(String threadName) {
        this.threadName = "Heimadallr-" + threadName;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, threadName);
    }
}

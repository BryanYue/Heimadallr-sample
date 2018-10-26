package com.githup.bryan.heimadallr_analyzer;

import android.content.Context;

import com.githup.bryan.heimadallr_analyzer.internal.HeimadallrInfo;

public interface HeimadallrInterceptor {
    void onBlock(Context context, HeimadallrInfo heimadallrInfo);
}

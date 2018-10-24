package com.githup.bryan.heimadallr;

import android.content.Context;

import com.githup.bryan.heimadallr.internal.HeimadallrInfo;

public interface HeimadallrInterceptor {
    void onBlock(Context context, HeimadallrInfo blockInfo);
}

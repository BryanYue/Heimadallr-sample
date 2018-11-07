package com.githup.bryan.heimadallr_android.upload;



/*
 * @author Bryan
 * @emil phantom3792@gmail.com
 * create at 2018/11/7 0007  13:23
 * description:   上传监听回调
 */

public interface UpLoadListener {
    void onUploading();

    void onUploadSuccess();

    void onError();

    void onPause();
}

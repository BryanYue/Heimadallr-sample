package com.githup.bryan.heimadallr_android.upload;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * @author Bryan
 * @emil phantom3792@gmail.com
 * create at 2018/11/7 0007  13:40
 * description:  状态
 */
public class Status {


    /**
     * 上传策略
     */
    @IntDef({UpLoadStrategy.UPLOAD_POLICY_REALTIME,
            UpLoadStrategy.UPLOAD_POLICY_WIFI_ONLY,
            UpLoadStrategy.UPLOAD_POLICY_BATCH,
            UpLoadStrategy.UPLOAD_POLICY_INTERVA,
            UpLoadStrategy.UPLOAD_POLICY_DEVELOPMENT,
            UpLoadStrategy.UPLOAD_POLICY_WHILE_INITIALIZE
    })
    @Retention(RetentionPolicy.RUNTIME)
    @interface UpLoadStrategy {
        //实时发送
        int UPLOAD_POLICY_REALTIME = 0;
        //只在wifi下
        int UPLOAD_POLICY_WIFI_ONLY = 1;
        //批量上报 达到一定次数
        int UPLOAD_POLICY_BATCH = 2;
        //时间间隔
        int UPLOAD_POLICY_INTERVA = 3;
        //开发者debug模式 调用就可以发送
        int UPLOAD_POLICY_DEVELOPMENT = 4;
        //每次启动 发送上次产生的数据
        int UPLOAD_POLICY_WHILE_INITIALIZE = 5;
    }


    /**
     * 上传状态
     */
    @IntDef({UpLoadStatus.UPLOAD_STATUS_START,
            UpLoadStatus.UPLOAD_STATUS_UPLOADING,
            UpLoadStatus.UPLOAD_STATUS_CANLE,
            UpLoadStatus.UPLOAD_STATUS_ERROR,
            UpLoadStatus.UPLOAD_STATUS_COMPLETED,
            UpLoadStatus.UPLOAD_STATUS_PAUSE,
            UpLoadStatus.UPLOAD_ERROR_FILE_NOT_FOUND,
            UpLoadStatus.UPLOAD_ERROR_IO_ERROR
    })
    @Retention(RetentionPolicy.RUNTIME)
    @interface UpLoadStatus {
        //上传开始
        int UPLOAD_STATUS_START = 10;
        //上传中
        int UPLOAD_STATUS_UPLOADING = 20;
        //上传结束
        int UPLOAD_STATUS_CANLE = 30;
        //上传错误
        int UPLOAD_STATUS_ERROR = 40;
        //上传完成
        int UPLOAD_STATUS_COMPLETED = 50;
        //上传暂停
        int UPLOAD_STATUS_PAUSE = 60;
        //未找到文件
        int UPLOAD_ERROR_FILE_NOT_FOUND = 70;
        //IO错误
        int UPLOAD_ERROR_IO_ERROR = 80;

    }

}

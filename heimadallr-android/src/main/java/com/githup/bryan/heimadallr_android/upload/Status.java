package com.githup.bryan.heimadallr_android.upload;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
 * @author Bryan
 * @emil phantom3792@gmail.com
 * create at 2018/11/7 0007  13:40
 * description:  状态参数
 */
public class Status {


    /**
     * 上传策略
     */
    @IntDef({UpLoadStrategy.REALTIME,
            UpLoadStrategy.WIFIONLY,
            UpLoadStrategy.BATCH,
            UpLoadStrategy.NTERVA,
            UpLoadStrategy.DEVELOPMENT,
            UpLoadStrategy.NITIALIZE
    })
    @Retention(RetentionPolicy.RUNTIME)
    @interface UpLoadStrategy {
        //实时发送
        int REALTIME = 0;
        //只在wifi下
        int WIFIONLY = 1;
        //批量上报 达到一定次数
        int BATCH = 2;
        //时间间隔
        int NTERVA = 3;
        //开发者debug模式 调用就可以发送
        int DEVELOPMENT = 4;
        //每次启动 发送上次产生的数据
        int NITIALIZE = 5;
    }


    /**
     * 上传状态
     */
    @IntDef({UpLoadStatus.START,
            UpLoadStatus.UPLOADING,
            UpLoadStatus.CANLE,
            UpLoadStatus.ERROR,
            UpLoadStatus.COMPLETED,
            UpLoadStatus.PAUSE,
            UpLoadStatus.FILE_NOT_FOUND,
            UpLoadStatus.IO_ERROR
    })
    @Retention(RetentionPolicy.RUNTIME)
    @interface UpLoadStatus {
        //上传开始
        int START = 10;
        //上传中
        int UPLOADING = 20;
        //上传结束
        int CANLE = 30;
        //上传错误
        int ERROR = 40;
        //上传完成
        int COMPLETED = 50;
        //上传暂停
        int PAUSE = 60;
        //未找到文件
        int FILE_NOT_FOUND = 70;
        //IO错误
        int IO_ERROR = 80;
    }

}

package com.hito.okplus;

import okhttp3.OkHttpClient;

/**
 * 对外暴露的网络请求类
 * get post 下载上传（通过回掉返回请求结果，以及上传和下载的进度）
 * 1，维护单例的OkHttpClient
 * 2，可设置自定义的OkHttpClient
 * 3，通过继承RequestBuilder，定义get与post方法
 *
 * Created by dream on 2017/02/08.
 */

public class OkHttpProxy {
    private static OkHttpClient mOkHttpClient;

    private static OkHttpClient init() {
        synchronized (OkHttpProxy.class) {
            if (mOkHttpClient == null) {
                return new OkHttpClient();
            } else {
                return mOkHttpClient;
            }
        }
    }

    public static OkHttpClient getHttpClient() {
        return mOkHttpClient == null ? init() : mOkHttpClient;
    }

    public static void setHttpClient(OkHttpClient okHttpClient) {
        OkHttpProxy.mOkHttpClient = okHttpClient;
    }
}

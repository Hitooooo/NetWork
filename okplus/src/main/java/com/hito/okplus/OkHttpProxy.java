package com.hito.okplus;

import com.hito.okplus.builder.GetRequestBuilder;
import com.hito.okplus.builder.PostRequestBuilder;

import okhttp3.OkHttpClient;

/**
 * 对外暴露的网络请求类
 * get post 下载上传（通过回掉返回请求结果，以及上传和下载的进度）
 * 1，维护单例的OkHttpClient
 * 2，可设置自定义的OkHttpClient
 * 3，通过继承RequestBuilder，定义get与post方法
 * <p>
 * Created by dream on 2017/02/08.
 */

public class OkHttpProxy {
    private static OkHttpClient mOkHttpClient;

    /**
     * 初始化
     */
    private static OkHttpClient init() {
        synchronized (OkHttpProxy.class) {
            if (mOkHttpClient == null) {
                return new OkHttpClient();
            } else {
                return mOkHttpClient;
            }
        }
    }

    /**
     * 获取当前设置的OkHttpClient，如果没有设置就重新new一个
     */
    public static OkHttpClient getHttpClient() {
        return mOkHttpClient == null ? init() : mOkHttpClient;
    }

    /**
     * 设置新的OkHttpClient
     *
     * @param okHttpClient 自定义OkHttpClient
     */
    public static void setHttpClient(OkHttpClient okHttpClient) {
        OkHttpProxy.mOkHttpClient = okHttpClient;
    }

    public static GetRequestBuilder get() {
        return new GetRequestBuilder();
    }

    public static PostRequestBuilder post() {
        return new PostRequestBuilder();
    }
}

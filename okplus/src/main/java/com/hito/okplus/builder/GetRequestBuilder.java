package com.hito.okplus.builder;

import android.text.TextUtils;

import com.hito.okplus.OkHttpProxy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Get请求方式的请求构造
 * 1，拼接参数 add param
 * 2，设置Tag可取消
 * <p>
 * Created by dream on 2017/02/08.
 */

public class GetRequestBuilder extends RequestBuilder {

    /**
     * 设置接口地址
     *
     * @param url 接口地址
     * @return GetRequestBuilder
     */
    public GetRequestBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 拼接参数
     * ?x=3&y=4
     *
     * @return
     */
    public GetRequestBuilder setParams(Map<String, String> params) {
        if (params != null) {
            this.mParams = params;
        }
        return this;
    }

    public GetRequestBuilder addParams(String key, String value) {
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        if (TextUtils.isEmpty(key) && TextUtils.isEmpty(value)) {
            this.mParams.put(key, value);
        }
        return this;
    }

    public GetRequestBuilder tag(Object tag) {
        this.mTag = tag;
        return this;
    }

    // 开始异步get请求
    public Call enqueue(Callback callback) {
        if (TextUtils.isEmpty(mUrl)) {
            throw new IllegalStateException("You must point a url");
        }
        Request.Builder builder = new Request.Builder();
        mUrl = appendParams(mUrl, mParams);
        builder.url(mUrl);
        if (mTag != null) {
            builder.tag(mTag);
        }
        Request request = builder.build();
        Call call = OkHttpProxy.getHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }

    // 开始同步get请求
    public Response execute() throws IOException {
        if (TextUtils.isEmpty(mUrl)) {
            throw new IllegalStateException("You must point a url");
        }
        Request.Builder builder = new Request.Builder();
        mUrl = appendParams(mUrl, mParams);
        builder.url(mUrl);
        if (mTag != null) {
            builder.tag(mTag);
        }
        Request request = builder.build();
        return OkHttpProxy.getHttpClient().newCall(request).execute();
    }


}

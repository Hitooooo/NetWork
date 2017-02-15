package com.hito.okplus.builder;

import android.text.TextUtils;

import com.hito.okplus.OkHttpProxy;

import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dream on 2017/02/13.
 * 进行Post请求
 */

public class PostRequestBuilder extends RequestBuilder {
    private Map<String, String> mHeaders;

    public PostRequestBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public PostRequestBuilder tag(Object tag) {
        this.mTag = tag;
        return this;
    }

    public PostRequestBuilder setParams(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    public PostRequestBuilder addParams(String key, String value) {
        if (mParams == null) {
            this.mParams = new HashMap<>();
        }
        mParams.put(key, value);
        return this;
    }

    public PostRequestBuilder headers(Map<String, String> headers) {
        this.mHeaders = headers;
        return this;
    }

    public PostRequestBuilder addHeader(String key, String values) {
        if (mHeaders == null) {
            mHeaders = new IdentityHashMap<>();
        }
        mHeaders.put(key, values);
        return this;
    }

    // 异步请求
    @Override
    public Call enqueue(Callback callback) {
        if (TextUtils.isEmpty(mUrl)) {
            throw new IllegalStateException("You must point a url");
        }
        Request.Builder builder = new Request.Builder().url(mUrl);
        if (mTag != null) {
            builder.tag(mTag);
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        appendParams(formBodyBuilder, mParams);
        appendHeaders(builder,mHeaders);
        builder.post(formBodyBuilder.build());
        Call call = OkHttpProxy.getHttpClient().newCall(builder.build());
        call.enqueue(callback);
        return  call;
    }

    // 同步请求
    @Override
    public Response execute() throws IOException {
        if (TextUtils.isEmpty(mUrl)) {
            throw new IllegalStateException("You must point a url");
        }
        Request.Builder builder = new Request.Builder().url(mUrl);
        if (mTag != null) {
            builder.tag(mTag);
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        appendParams(formBodyBuilder, mParams);
        appendHeaders(builder,mHeaders);
        builder.post(formBodyBuilder.build());
        Call call = OkHttpProxy.getHttpClient().newCall(builder.build());
        return call.execute();
    }


}

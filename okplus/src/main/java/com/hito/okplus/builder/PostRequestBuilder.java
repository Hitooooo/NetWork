package com.hito.okplus.builder;

import android.text.TextUtils;

import com.hito.okplus.OkHttpProxy;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by dream on 2017/02/13.
 * 进行Post请求
 */

public class PostRequestBuilder {
    private String              mUrl; // 接口
    private Object              mTag; // tag
    private Map<String, String> mParams;

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

    public void enqueue(Callback callback) {
        if (TextUtils.isEmpty(mUrl)) {
            throw new IllegalStateException("You must point a url");
        }
        Request.Builder builder = new Request.Builder().url(mUrl);
        if (mTag != null) {
            builder.tag(mTag);
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        appendParams(formBodyBuilder, mParams);
        builder.post(formBodyBuilder.build());
        Call call = OkHttpProxy.getHttpClient().newCall(builder.build());
        call.enqueue(callback);
    }

    public void appendParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null && params.size() > 0) {
            for (String s : params.keySet()) {
                builder.add(s, params.get(s));
            }
        }
    }
}

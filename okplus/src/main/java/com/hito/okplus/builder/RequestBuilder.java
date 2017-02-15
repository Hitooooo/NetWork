package com.hito.okplus.builder;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 正常请求：通过Builder构造Request，OkHttpClient.newCall()传入Request和回调
 * 对于get拼接参数麻烦，返回的数据每次都得处理
 * 对于post每次都需要new FormBody.Builder()设置Request
 * 最重要的是：拦截Response，处理后直接返回可操作对象
 * 此类就是两种请求的父类Builder，通过此类的子类直接获取需要的builder，通过这个Builder构造需要的RequestBuilder
 * Created by dream on 2017/02/08.
 */

public abstract class RequestBuilder {

    protected String mUrl; //接口地址

    protected Map<String, String> mParams; // 请求参数

    protected Object mTag; // 标记

    protected abstract Call enqueue(Callback callback);

    protected abstract Response execute() throws IOException;

    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    // 拼接Url
    protected String appendParams(String url, Map<String, String> params) {
        if (params == null || params.size() < 1) {
            return url;
        } else {
            StringBuilder sb = new StringBuilder(url).append("?");
            for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
                sb.append(stringStringEntry.getKey()).append("=").append(stringStringEntry
                        .getValue()).append("&");
            }
            return sb.toString();
        }
    }

    protected void appendParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null && params.size() > 0) {
            for (String s : params.keySet()) {
                builder.add(s, params.get(s));
            }
        }
    }
}

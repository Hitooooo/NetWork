package com.mht.baselib.net;

import android.content.Context;
import android.os.Handler;

import com.mht.baselib.net.callback.NetCallback;
import com.mht.baselib.net.response.IResponseHandler;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 描述 网络请求.
 * <p>
 * Created by Mht on 2017/2/6.
 */
public class Net {
    private static Net ourInstance = new Net();
    private OkHttpClient mClient;

    public static Net getInstance() {
        return ourInstance;
    }

    private Net() {
        mClient = new OkHttpClient();
    }

    /**
     * get请求
     */
    public void get(final String url, final Map<String, String> params, final IResponseHandler responseHandler) {
        get(null, url, params, responseHandler);
    }

    public void get(Context context, String url, Map<String, String> params, IResponseHandler responseHandler) {
        String requestUrl = url;
        if (params != null && params.size() > 0) {
            int count = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (count++ == 0) {
                    requestUrl = requestUrl + "?" + entry.getKey() + "=" + entry.getValue();
                } else {
                    requestUrl = requestUrl + "&" + entry.getKey() + "=" + entry.getValue();
                }
            }
        }
        Request request;
        if (context == null) {
            request = new Request.Builder()
                    .url(requestUrl)
                    .build();
        } else {    // Context不为空的话,就设置Tag用来取消请求
            request = new Request.Builder()
                    .url(requestUrl)
                    .tag(context)
                    .build();
        }
        mClient.newCall(request).enqueue(new NetCallback(new Handler(), responseHandler));
    }
}

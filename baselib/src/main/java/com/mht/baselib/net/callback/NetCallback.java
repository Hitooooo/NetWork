package com.mht.baselib.net.callback;

import android.os.Handler;

import com.google.gson.Gson;
import com.mht.baselib.net.response.GsonResponseHandler;
import com.mht.baselib.net.response.IResponseHandler;
import com.mht.baselib.net.response.JsonResponseHandler;
import com.mht.baselib.net.response.RawResponseHandler;
import com.mht.baselib.net.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 描述 TODO
 * Created by Mht on 2017/2/6.
 */

public class NetCallback implements Callback {

    private Handler mHandler;
    private IResponseHandler mResponseHandler;

    /**
     * todo handler 是否可以内置
     *
     * @param handler          切换线程的Handler
     * @param iResponseHandler 结果回调
     */
    public NetCallback(Handler handler, IResponseHandler iResponseHandler) {
        mHandler = handler;
        mResponseHandler = iResponseHandler;
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        LogUtils.e("onFailure", e);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mResponseHandler.onFailure(0, e.toString());
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {// 服务器正确响应
            final String msg = response.body().string();
            if (mResponseHandler instanceof GsonResponseHandler) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        GsonResponseHandler gsonResponseHandler = (GsonResponseHandler) mResponseHandler;
                        Gson gson = new Gson();
                        try {
                            gsonResponseHandler.onSuccess(response.code(), gson.fromJson(msg, gsonResponseHandler.getType()));
                        } catch (Exception e) {// 打印错误日志,ResponseHandler走失败
                            LogUtils.e("onResponse parse json failed. ResponseBody = " + msg, e);
                            gsonResponseHandler.onFailure(response.code(), "onResponse parse json failed. ResponseBody = " + msg);
                        }
                    }
                });
            } else if (mResponseHandler instanceof JsonResponseHandler) {// 转换JSONObject,存在失败与成功
                final JsonResponseHandler jsonResponseHandler = (JsonResponseHandler) mResponseHandler;
                try {
                    final JSONObject jsonObject = new JSONObject(msg);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            jsonResponseHandler.onSuccess(response.code(), jsonObject);
                        }
                    });
                } catch (final JSONException e) {
                    LogUtils.e("onResponse parse json failed. ResponseBody = " + msg, e);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            jsonResponseHandler.onFailure(response.code(),e.toString());
                        }
                    });
                }
            } else if (mResponseHandler instanceof RawResponseHandler) {
                final RawResponseHandler rawResponseHandler = (RawResponseHandler) mResponseHandler;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        rawResponseHandler.onSuccess(response.code(),msg);
                    }
                });
            }
        } else { // 服务器已相应,但是未找到相关资源
            LogUtils.e("onFailure", "server response code" + response.code());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseHandler.onFailure(0, "failure status =" + response.code());
                }
            });
        }
    }
}

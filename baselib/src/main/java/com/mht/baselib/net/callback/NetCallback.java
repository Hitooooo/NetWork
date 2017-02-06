package com.mht.baselib.net.callback;

import android.os.Handler;

import com.mht.baselib.net.response.IResponseHandler;
import com.mht.baselib.net.util.LogUtils;

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
            LogUtils.i(response.body().toString());
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

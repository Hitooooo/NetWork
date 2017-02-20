package com.hito.okplus.callback;

import android.os.Handler;
import android.os.Looper;

import com.hito.okplus.model.BaseBean;
import com.hito.okplus.parser.OkBaseParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by dream on 2017/02/18.
 */

public abstract class OkCallback<T> implements Callback {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private OkBaseParser mOkBaseParser;


    public OkCallback(OkBaseParser okBaseParser) {
        this.mOkBaseParser = okBaseParser;
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onNo(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, Response response) {
        final int responseCode = response.code();
        if (response.isSuccessful()) {
            final BaseBean<T> baseBean;
            try {
                 baseBean = mOkBaseParser.parseResponse(response);
                if (baseBean != null) {
                    onOk(responseCode, baseBean);
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onNo(call,new Exception(Integer.toString(responseCode)));
                        }
                    });
                }
            } catch (final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNo(call, e);
                    }
                });
            }
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onNo(call,new Exception(Integer.toString(responseCode)));
                }
            });
        }
    }

    public abstract void onOk(int code,BaseBean<T> Q);

    public abstract void onNo(Call call, Exception e);
}

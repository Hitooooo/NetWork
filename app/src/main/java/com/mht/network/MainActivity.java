package com.mht.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.mht.baselib.net.Net;
import com.mht.baselib.net.response.RawResponseHandler;
import com.mht.baselib.net.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_hello)
    Button mBtnHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_hello)
    public void onClick() {
        Net.getInstance().get("http://gank.io/api/data/Android/10/1", null, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                LogUtils.i(response);
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LogUtils.i(error_msg);
            }
        });
    }
}

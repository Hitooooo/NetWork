package com.mht.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hito.okplus.OkHttpProxy;
import com.mht.baselib.net.Net;
import com.mht.baselib.net.response.RawResponseHandler;
import com.mht.baselib.net.util.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_hello)
    Button mBtnHello;
    @BindView(R.id.btn_get_plus)
    Button mBtnGetPlus;
    @BindView(R.id.btn_post_plus)
    Button mBtnPostPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_hello, R.id.btn_get_plus,R.id.btn_post_plus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hello:
                getInOkFinal();
                break;
            case R.id.btn_get_plus:
                getInOkPlus();
                break;
            case R.id.btn_post_plus:
                postInOkPlus();
                break;
        }
    }

    private void postInOkPlus() {
        Map<String, String> params = new HashMap<>();
        params.put("url", "www.baidu.com");
        params.put("desc", "www.baidu.com");
        params.put("who", "www.baidu.com");
        params.put("type", "瞎推荐");
        params.put("debug", "true");

        OkHttpProxy.post().url("https://gank.io/api/add2gank")
                            .setParams(params).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.i(e.toString() + "你失败了..");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.i(response.body().string() + "你成功啦");

            }
        });
    }

    public void getInOkPlus() {
        OkHttpProxy.get().url("http://gank.io/api/data/Android/10/1").tag(this).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.i(e.toString() + "你失败了..");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.i(response.body().string() + "你成功啦");
            }
        });
    }

    private void getInOkFinal() {
        Net.getInstance().get("http://gank.io/api/data/Android/10/1", null, new
                RawResponseHandler() {
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

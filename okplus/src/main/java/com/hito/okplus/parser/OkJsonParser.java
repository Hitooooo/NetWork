package com.hito.okplus.parser;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hito.okplus.model.BaseBean;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by dream on 2017/02/20.
 */

public class OkJsonParser<T> extends OkBaseJsonPaser<T> {

    private Gson mGson;
    private Type mNewType;

    public OkJsonParser(Type type) {
        mNewType = type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Gson过滤 final,transient,static的成员变量 // TODO: 2017/02/20 为什么版本大于23要处理
            GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithModifiers(Modifier
                    .FINAL, Modifier.TRANSIENT, Modifier.STATIC);
            mGson = gsonBuilder.create();
        } else {
            mGson = new Gson();
        }
    }

    @Override
    protected BaseBean<T> parse(Response response) throws IOException {

        return mGson.fromJson(response.body().string(), mNewType);
    }
}

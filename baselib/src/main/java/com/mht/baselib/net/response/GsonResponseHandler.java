package com.mht.baselib.net.response;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 描述 可根据泛型直接返回可操作对象
 * Created by Mht on 2017/2/6.
 */

public abstract class GsonResponseHandler<T> implements IResponseHandler {
    private Type mType;

    public GsonResponseHandler() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) type;
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
    }

    public abstract void onSuccess(int statusCode, T response);

    /**
     * 无进度显示,空实现即可
     * @param currentBytes
     * @param totalBytes
     */
    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }

    public Type getType() {
        return mType;
    }
}

package com.mht.baselib.net.response;

import org.json.JSONObject;

/**
 * 描述 返回JsonObject
 * Created by Mht on 2017/2/6.
 */

public abstract class JsonResponseHandler implements IResponseHandler {

    public abstract void onSuccess(int statueCode, JSONObject jsonObject);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}

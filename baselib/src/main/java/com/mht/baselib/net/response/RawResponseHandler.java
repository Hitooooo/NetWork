package com.mht.baselib.net.response;

/**
 * 描述 单纯回调String
 * Created by Mht on 2017/2/6.
 */

public abstract class RawResponseHandler implements IResponseHandler {

    public abstract void onSuccess(int statusCode, String response);

    // 空实现
    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}

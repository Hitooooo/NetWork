package com.mht.baselib.net.response;

/**
 * 描述 TODO
 * Created by Mht on 2017/2/6.
 */

public interface IResponseHandler {
    void onFailure(int statusCode, String error_msg);

    void onProgress(long currentBytes, long totalBytes);
}

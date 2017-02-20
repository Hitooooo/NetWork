package com.hito.okplus.parser;

import com.hito.okplus.model.BaseBean;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by dream on 2017/02/18.
 * json解析，字符串解析等解析器的顶层基类
 *
 * 1,定义基本功能
 * 2，抽取了公共的返回当前Type的方法
 */

public abstract class OkBaseParser<T>  {

    private int mStatusCode;

    public OkBaseParser() {
    }

    /**
     * 强制子类实现解析方法
     * @param response
     * @return
     */
    protected abstract BaseBean<T> parse(Response response) throws IOException;

    public BaseBean<T> parseResponse(Response response) throws IOException {
        this.mStatusCode = response.code();
        return parse(response);
    }

    /**
     * @return mStatusCode 网络回执码
     */
    public int getStatusCode(){
        return mStatusCode;
    }


}

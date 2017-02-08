package com.hito.okplus.builder;

/**
 * 正常请求：通过Builder构造Request，OkHttpClient.newCall()传入Request和回调
 * 对于get拼接参数麻烦，返回的数据每次都得处理
 * 对于post每次都需要new FormBody.Builder()设置Request
 *      最重要的是：拦截Response，处理后直接返回可操作对象
 * 此类就是中间Builder，通过此类的子类直接获取需要的builder，通过这个Builder构造需要的RequestBuilder
 * Created by dream on 2017/02/08.
 */

public abstract class RequestBuilder {
}

package com.hito.okplus.parser;

import com.hito.okplus.model.BaseBean;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Json解析基类
 * Created by dream on 2017/02/20.
 */

public abstract class OkBaseJsonPaser<T> extends OkBaseParser<T> {

    protected Type mType;

    public OkBaseJsonPaser() {
        mType = getSuperclassTypeParameter(getClass());
    }


    protected abstract BaseBean<T> parse(Response response) throws IOException ;

    /**
     * 获取超类的类型
     *
     * @param subclass
     * @return
     */
    protected Type getSuperclassTypeParameter(Class<?> subclass) {
//        Type superclass = subclass.getGenericSuperclass();
//        if (superclass instanceof Class) {
//            throw new RuntimeException("Missing type parameter.");
//        }
//        ParameterizedType parameter = (ParameterizedType) superclass;
//        return $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
        return null;
    }
}

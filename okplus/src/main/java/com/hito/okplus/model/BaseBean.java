package com.hito.okplus.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dream on 2017/02/19.
 */

public class BaseBean<T> {
    @SerializedName("error")
    protected boolean error;


    @SerializedName("results")
    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

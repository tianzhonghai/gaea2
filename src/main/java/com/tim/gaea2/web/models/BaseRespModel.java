package com.tim.gaea2.web.models;

/**
 * Created by Tim on 2017/6/25.
 */
public class BaseRespModel<T> {
    private int status;
    private String message;
    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

package com.cdu.lys.graduation.commons.result;

import java.io.Serializable;

/**
 * @author liyinsong
 * @date 2022/1/8 10:19
 */
public class ActionResult<T> implements Serializable {
    private String code;
    private String message;
    private boolean success;
    private T data;

    public ActionResult() {
    }

    public ActionResult(String code, String message, boolean success, T data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static <T> ActionResult<T> getSuccessResult(String message) {
        return new ActionResult<>("0000", message, true, null);
    }
    public static <T> ActionResult<T> getSuccessResult(T data){
        return new ActionResult<>("0000", "成功", true, data);
    }

    public static <T> ActionResult<T> getSuccessResult(String message, T data) {
        return new ActionResult<>("0000", message, true, data);
    }

    public static <T> ActionResult<T> getSuccessResult(String code, String message, T data) {
        return new ActionResult<>(code, message, true, data);
    }

    public static <T> ActionResult<T> getErrorResult(String code, String message) {
        return new ActionResult<>(code, message, false, null);
    }

    public static <T> ActionResult<T> getErrorResult(String code, String message, T data) {
        return new ActionResult<>(code, message, false, data);
    }

    public static <T> ActionResult<T> getErrorResult(String message, T data) {
        return new ActionResult<>("9999", message, false, data);
    }

    public static <T> ActionResult<T> getErrorResult(String message) {
        return new ActionResult<>("9999", message, false, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ActionResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}

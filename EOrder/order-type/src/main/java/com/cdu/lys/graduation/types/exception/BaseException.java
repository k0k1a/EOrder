package com.cdu.lys.graduation.types.exception;

import lombok.Getter;

/**
 * 系统自定义异常
 * @author liyinsong
 * @date 2022/1/8 11:38
 */
@Getter
public class BaseException extends RuntimeException{

    private String code;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


}

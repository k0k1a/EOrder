package com.cdu.lys.graduation.types.exception;

/**
 * 业务异常
 * @author liyinsong
 * @date 2022/1/8 11:44
 */
public class BusinessException extends BaseException{


    public BusinessException(ErrorType errorType, String message, Throwable cause) {
        super(errorType.getCode(), message, cause);
    }

    public BusinessException(ErrorType errorType,String message){
        super(errorType.getCode(),message);
    }
}

package com.cdu.lys.graduation.types.exception;

/**
 * 参数异常
 * @author liyinsong
 * @date 2022/1/8 13:44
 */
public class ParamException extends BaseException{

    public ParamException(ErrorType errorType,String message){
        super(errorType.getCode(), message);
    }
}

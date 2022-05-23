package com.cdu.lys.graduation.types.exception;

/**
 * @author liyinsong
 * @date 2022/3/28 22:10
 */
public class FileException extends BaseException{

    public FileException(ErrorType errorType, String message) {
        super(errorType.getCode(), message);
    }

    public FileException(ErrorType errorType, String message, Throwable cause) {
        super(errorType.getCode(), message, cause);
    }
}
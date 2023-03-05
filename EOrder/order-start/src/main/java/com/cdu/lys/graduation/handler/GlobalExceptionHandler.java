package com.cdu.lys.graduation.handler;

import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.types.exception.BusinessException;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.cdu.lys.graduation.types.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * @author liyinsong
 * @date 2022/1/8 11:01
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ActionResult<Void> exceptionHandler(Exception e) {
        log.error("system global exception handler catch. exception is", e);
        return ActionResult.getErrorResult("9999", "未知错误");
    }

    @ExceptionHandler(BusinessException.class)
    public ActionResult<Void> businessExceptionHandler(BusinessException e) {
        log.error("system caught business exception. code:{}, message:{}", e.getCode(), e.getMessage(), e);
        return ActionResult.getErrorResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ParamException.class)
    public ActionResult<Void> paramExceptionHandler(ParamException e) {
        log.error("system caught paramException. code:{}, message:{}", e.getCode(), e.getMessage(), e);
        return ActionResult.getErrorResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ActionResult<Object> bindExceptionHandler(BindException e) {

        List<String> defaultMsg = e.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        log.error("system caught BindException, message:{}", defaultMsg.get(0), e);
        return ActionResult.getErrorResult(ErrorType.PARAM_ERROR.getCode(), defaultMsg.get(0));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ActionResult<Object> constraintViolationExceptionHandler(ConstraintViolationException e) {

        List<String> defaultMsg = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        log.error("system caught ConstraintViolationException, message:{}", defaultMsg.get(0), e);
        return ActionResult.getErrorResult(ErrorType.PARAM_ERROR.getCode(), e.getMessage());
    }

}

package com.cdu.lys.graduation.interceptor;

import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.cdu.lys.graduation.types.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liyinsong
 * @date 2022/4/1 0:32
 */
@Component
@Slf4j
public class FileInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String requestURI = request.getRequestURI();

        //预检请求
        if ("OPTIONS".equals(request.getMethod())) {
            log.info("CORS OPTIONS URI:{{}}", requestURI);
            return true;
        }

        //未携带token
        if (!StringUtils.hasLength(token)) {
            log.error("request URI{{}}, no token error", requestURI);
            throw new ParamException(ErrorType.PARAM_USER_AUTH, ErrorType.PARAM_USER_AUTH.getMessage());
        }

        //校验合法性
        if (!redisService.checkTokenValid(token) && !redisService.checkAdminTokenValid(token)) {
            log.error("request RUI{{}}, the token is invalid", requestURI);
            throw new ParamException(ErrorType.PARAM_ERROR, "不合法token:" + token);
        }
        log.info("request RUI{{}}, the token is valid", requestURI);
        return true;
    }

}

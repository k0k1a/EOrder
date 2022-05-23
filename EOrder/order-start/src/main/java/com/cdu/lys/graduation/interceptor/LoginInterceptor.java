package com.cdu.lys.graduation.interceptor;

import com.alibaba.fastjson.JSON;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.cdu.lys.graduation.types.exception.ParamException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户拦截器
 *
 * @author liyinsong
 * @date 2022/1/8 14:42
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedisService redisService;

    private final AntPathMatcher matcher = new AntPathMatcher();

    /**
     * 登录接口白名单
     */
    private final List<String> whiteList = Lists.newArrayList(
            "/eorder/app/goods/**",
            "/eorder/**/login/**",
            "/eorder/**/visit/**");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        //白名单
        String requestURI = request.getRequestURI();
        if (isWhiteList(requestURI) || "OPTIONS".equals(request.getMethod())) {
            redisService.incrVisitNumber();
            log.info("request RUI{{}} is whitelist", requestURI);
            return true;
        }

        //未携带token
        if (!StringUtils.hasLength(token)) {
            log.error("request RUI{{}}, no token error", requestURI);
            throw new ParamException(ErrorType.PARAM_USER_AUTH, ErrorType.PARAM_USER_AUTH.getMessage());
        }

        //校验合法性
        if (!redisService.checkTokenValid(token)) {
            log.error("request RUI{{}}, the token is invalid", requestURI);
            throw new ParamException(ErrorType.PARAM_ERROR, "不合法token:" + token);
        }
        redisService.incrVisitNumber();
        log.info("request RUI{{}}, the token is valid", requestURI);
        return true;
    }

    /**
     * 错误信息写入
     *
     * @param message  错误信息
     * @param response
     * @throws IOException
     */
    private void writeError(Object message, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        IOUtils.write(JSON.toJSONString(message), response.getWriter());
    }

    /**
     * 是否在白名单列表
     * @param requestUrl 请求url
     * @return true/false
     */
    private boolean isWhiteList(String requestUrl) {
        for (String url : whiteList) {
            if (matcher.match(url, requestUrl)) {
                return true;
            }
        }
        return false;
    }

}

package com.cdu.lys.graduation.interceptor;

import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.cdu.lys.graduation.types.exception.ParamException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/28 09:55
 */
@Component
@Slf4j
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    private final AntPathMatcher matcher = new AntPathMatcher();

    /**
     * 登录接口白名单
     */
    private final List<String> whiteList = Lists.newArrayList(
            "/eorder/**/login/**");


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (!StringUtils.hasLength(token)) {
            token = request.getParameter("token");
        }
        //白名单
        String requestURI = request.getRequestURI();
        if (isWhiteList(requestURI) || "OPTIONS".equals(request.getMethod())) {
            log.info("Admin request RUI{{}} is whitelist", requestURI);
            return true;
        }

        //未携带token
        if (!StringUtils.hasLength(token)) {
            log.error("Admin request RUI{{}}, no token error", requestURI);
            throw new ParamException(ErrorType.PARAM_USER_AUTH, ErrorType.PARAM_USER_AUTH.getMessage());
        }

        //校验token是否有效
        if (!redisService.checkAdminTokenValid(token)) {
            log.error("Admin request RUI{{}}, the token is invalid", requestURI);
            throw new ParamException(ErrorType.PARAM_ERROR, "不合法token:" + token);
        }

        log.info("Admin request RUI{{}}, the token is valid", requestURI);
        return true;
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

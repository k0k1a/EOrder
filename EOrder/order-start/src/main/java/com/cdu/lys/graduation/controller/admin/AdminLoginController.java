package com.cdu.lys.graduation.controller.admin;

import com.cdu.lys.graduation.admin.login.service.AdminLoginService;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.utils.JWTUtils;
import com.cdu.lys.graduation.domain.admin.TokenValue;
import com.cdu.lys.graduation.domain.user.dto.UserDTO;
import com.cdu.lys.graduation.domain.user.vo.AdminUserLoginVO;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.types.SystemConstant;
import com.cdu.lys.graduation.types.user.LoginQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 管理系统接口
 *
 * @author liyinsong
 * @date 2022/3/16 17:25
 */
@RestController
@RequestMapping(value = "/eorder/admin")
@Api(tags = "管理系统登录接口")
@Slf4j
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${system.order.secretKey}")
    private String secretKey;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("管理系统登录")
    public ActionResult<Object> login(@RequestBody @Valid LoginQuery query) {
        UserDTO userDTO = adminLoginService.login(query);
//        JWT.create().withAudience(query.getUsername()).sign(Algorithm.HMAC256(query.getPassword()));

        if (userDTO == null) {
            log.info("Admin login failed, username:{}", query.getUsername());
            return ActionResult.getErrorResult("登录失败");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", query.getUsername());
        String token = JWTUtils.createJWT("admin", TimeUnit.DAYS.toMillis(1), claims, secretKey);

        //存入redis
        redisTemplate.opsForValue().set(SystemConstant.ADMIN_LOGIN_TOKEN_KEY + query.getUsername(), new TokenValue(token), SystemConstant.SYSTEM_LOGIN_TIMEOUT, TimeUnit.DAYS);

        AdminUserLoginVO loginVO = new AdminUserLoginVO();
        BeanUtils.copyProperties(userDTO, loginVO);
        loginVO.setToken(token);

        log.info("Admin login success, username:{}", query.getUsername());
        return ActionResult.getSuccessResult("登录成功", loginVO);
    }

    @ApiOperation("注销登录")
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> wxLogout(@RequestHeader String token) {
        String username = (String) JWTUtils.parseJWT(token, secretKey).get("username");

        String key = SystemConstant.ADMIN_LOGIN_TOKEN_KEY + username;
        Boolean delete = redisTemplate.delete(key);

        if (delete) {
            log.info("Admin delete key [{}] success", key);
            return ActionResult.getSuccessResult("注销成功");
        } else {
            log.info("Admin delete key [{}] fail", token);
            return ActionResult.getErrorResult("注销成功");
        }
    }

    @GetMapping(value = "/token/check", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("校验登录token是否失效")
    public ActionResult<Object> checkSessionExpire(@RequestHeader("token") String token) {

        boolean valid = redisService.checkAdminTokenValid(token);
        if (!valid) {
            log.warn("token is invalid");
            return ActionResult.getErrorResult("不合法token");
        }
        log.info("token is valid");
        return ActionResult.getSuccessResult("合法token");
    }

}

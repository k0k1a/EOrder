package com.cdu.lys.graduation.controller.user;

import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.utils.JWTUtils;
import com.cdu.lys.graduation.domain.user.dto.WxUserDTO;
import com.cdu.lys.graduation.domain.user.vo.LoginVO;
import com.cdu.lys.graduation.domain.user.vo.UserVO;
import com.cdu.lys.graduation.domain.user.wx.LoginUser;
import com.cdu.lys.graduation.mini.user.bo.UserBO;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.types.SystemConstant;
import com.cdu.lys.graduation.types.user.LoginQuery;
import com.cdu.lys.graduation.types.user.UserQuery;
import com.cdu.lys.graduation.types.user.WxLoginQuery;
import com.cdu.lys.graduation.types.user.WxUserProfile;
import com.cdu.lys.graduation.mini.user.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * 用户接口
 *
 * @author liyinsong
 * @date 2022/1/8 15:27
 */
@Api(tags = "User登录接口")
@RestController
@RequestMapping("/eorder/app/user")
@Slf4j
@Validated
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${system.order.secretKey}")
    private String secretKey;

    @Autowired
    private UserBO userBO;

    /**
     * 系统登录
     *
     * @param loginQuery 用户输入
     * @return 登录结果
     */
    @ApiOperation("系统登录逻辑")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> login(@RequestBody @Valid LoginQuery loginQuery) {
        ActionResult<Object> result = loginService.systemLogin(loginQuery);
        if (!result.isSuccess()) {
            log.warn("user[{}] login failed, err msg: {}", loginQuery.getUsername(), result.getMessage());
            return result;
        }
        //生成登录状态jwt token
        String token = JWTUtils.createJWT(secretKey);

        //存入redis设置失效时间，3天
        Object data = result.getData();
        redisTemplate.opsForValue().set(SystemConstant.LOGIN_TOKEN_KEY + token, data, SystemConstant.SYSTEM_LOGIN_TIMEOUT, TimeUnit.DAYS);

        UserVO userVO = new UserVO();
        LoginVO loginVO = new LoginVO();

        BeanUtils.copyProperties(data, userVO);
        loginVO.setToken(token);
        loginVO.setUserInfo(userVO);

        log.info("user[{}] login success.", loginQuery.getUsername());
        return ActionResult.getSuccessResult(result.getMessage(), loginVO);
    }

    /**
     * 微信登录
     * @param loginQuery 用户登录凭证
     * @return 登录
     */
    @ApiOperation("微信登录逻辑")
    @PostMapping(value = "/wx/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> wxLogin(@RequestBody @Valid WxLoginQuery loginQuery) {
        log.info("UserController#wxLogin, data: {}", loginQuery);

        ActionResult<Object> loginResult = loginService.wxLogin(loginQuery.getCode());

        //登录失败
        if (!loginResult.isSuccess()) {
            log.error("UserController#wxLogin fail, error msg:{}", loginResult.getMessage());
            return loginResult;
        }

        //生成登录状态jwt token
        String token = JWTUtils.createJWT(secretKey);

        // TODO 这里应该不是存Redis
        //存入redis设置失效时间，3天
        redisTemplate.opsForValue().set(SystemConstant.LOGIN_TOKEN_KEY + token, loginResult.getData(), SystemConstant.SYSTEM_LOGIN_TIMEOUT, TimeUnit.DAYS);

        //将token，返回前端
        LoginVO wxLoginVO=new LoginVO();
        wxLoginVO.setToken(token);

        log.info("UserController#wxLogin success, generated token is [{}]", token);
        return ActionResult.getSuccessResult("登录成功", wxLoginVO);
    }

    @ApiOperation("注册微信用户，插入数据")
    @PostMapping(value = "/wx/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> insertWxUser(@RequestBody WxUserProfile user, @RequestHeader("token") String token) {
        //获取用户session_key
        LoginUser loginUser = redisService.getLoginValue(token);
        String sessionKey = loginUser.getSession_key();
        //校验用户数据是否被修改
        boolean isChanged = loginService.checkWxUser(user.getRawData(), user.getSignature(), sessionKey);
        if (!isChanged) {
            log.info("user data has changes, check user failed");
            return ActionResult.getErrorResult("用户数据被篡改");
        }
        log.info("user data no changes, check user success");

        WxUserDTO wxUserDTO = null;

        try {
            //解密用户数据
            wxUserDTO = loginService.decryptWxUserInfo(user.getEncryptedData(), user.getIv(), sessionKey);
            log.info("WeChat UserInfo decrypt success");
        } catch (Exception e) {
            log.error("WeChat UserInfo decrypt failed", e);
            return ActionResult.getErrorResult("用户数据解密失败");
        }

        //插入数据库
        wxUserDTO.setOpenId(loginUser.getOpenid());
        loginService.registryWxUserInfo(wxUserDTO);

        return ActionResult.getSuccessResult("用户数据插入成功");
    }

    @ApiOperation("注销登录")
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> wxLogout(@RequestHeader String token) {
        Boolean delete = redisTemplate.delete(SystemConstant.LOGIN_TOKEN_KEY + token);

        if (delete) {
            log.info("delete key [{}] success", token);
            return ActionResult.getSuccessResult("注销成功");
        } else {
            log.info("delete key [{}] fail", token);
            return ActionResult.getErrorResult("注销成功");
        }
    }

    @ApiOperation("获取微信用户手机号")
    @GetMapping(value = "/wx/getPhoneNumber")
    public void wxGetWxPhoneNumber(String code){
        loginService.getWxUserPhoneNumber(code);

    }

    @GetMapping(value = "/token/check")
    @ApiOperation("校验登录token是否失效")
    public ActionResult<Object> checkSessionExpire(@RequestHeader("token") String token){
        boolean valid = redisService.checkTokenValid(token);
        if (!valid) {
            log.warn("token is invalid");
            return ActionResult.getErrorResult("不合法token");
        }
        log.info("token is valid");
        return ActionResult.getSuccessResult("合法token");
    }

    @ApiOperation("修改用户信息")
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> modifyUser(@RequestBody @Valid UserQuery query, @RequestHeader("token") String token) {
        LoginUser loginUser = redisService.getLoginValue(token);

        int i = userBO.updateUser(query, loginUser.getId());

        log.info("user modify userInfo success");
        return ActionResult.getSuccessResult("保存成功");
    }

    @ApiOperation("获取用户信息")
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<UserVO> getUserInfo(@RequestHeader("token") String token) {
        LoginUser loginUser = redisService.getLoginValue(token);

        UserDO userDO = userBO.getUserById(loginUser.getId());
        UserVO userVO = new UserVO();

        BeanUtils.copyProperties(userDO, userVO);
        log.info("select userInfo success");
        return ActionResult.getSuccessResult(userVO);
    }
}

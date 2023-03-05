package com.cdu.lys.graduation.mini.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.utils.BASE64Utils;
import com.cdu.lys.graduation.domain.user.dto.UserDTO;
import com.cdu.lys.graduation.domain.user.dto.WxUserDTO;
import com.cdu.lys.graduation.domain.user.wx.WxAccessTokenResponse;
import com.cdu.lys.graduation.domain.user.wx.WxLoginResponse;
import com.cdu.lys.graduation.domain.user.wx.WxPhoneInfo;
import com.cdu.lys.graduation.mini.user.service.LoginService;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.repository.dao.UserDOMapper;
import com.cdu.lys.graduation.repository.dao.ext.UserDOMapperExt;
import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.types.SystemConstant;
import com.cdu.lys.graduation.types.exception.BusinessException;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.cdu.lys.graduation.types.user.LoginQuery;
import com.cdu.lys.graduation.types.user.UserConstant;
import com.cdu.lys.graduation.types.user.UserGenderEnum;
import com.cdu.lys.graduation.types.user.UserTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Objects;

/**
 * User业务逻辑
 *
 * @author liyinsong
 * @date 2022/1/8 17:01
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Value("${order.wx.AppID}")
    private String appId;

    private String appSecret;

    @Value("${login.system.salt}")
    private String systemSalt;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserDOMapperExt userDOMapperExt;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Value(("${order.wx.AppSecret}"))
    public void setAppSecret(String appSecret){
        this.appSecret = BASE64Utils.parseBase64(appSecret);
    }

    @Override
    public ActionResult<Object> systemLogin(LoginQuery loginQuery) {
        UserDO user = userDOMapperExt.selectByUsername(loginQuery.getUsername());
        UserDTO userDTO = new UserDTO();

        if (user == null) {
            //注册
            String bcryptPassword = this.bcryptPassword(loginQuery.getPassword());

            UserDO userDO = new UserDO();
            userDO.setCreateTime(new Date());
            userDO.setUsername(loginQuery.getUsername());
            userDO.setNickname(UserConstant.DEFAULT_USER_NICKNAME);
            userDO.setPassword(bcryptPassword);
            userDO.setPhone(loginQuery.getUsername());
            userDO.setType(UserTypeEnum.NATIVE_USER.getCode());
            userDO.setGender(UserGenderEnum.UNKNOWN.getCode());

            userDOMapper.insertSelective(userDO);
            BeanUtils.copyProperties(userDO, userDTO);

            log.info("insert wx user success.");
            return ActionResult.getSuccessResult("注册会员成功", userDTO);
        }

        //校验用户输入密码
        if (!this.decodeBCryptPassword(loginQuery.getPassword(), user.getPassword())) {
            return ActionResult.getErrorResult("用户名密码错误");
        }

        //校验成功
        BeanUtils.copyProperties(user, userDTO);

        return ActionResult.getSuccessResult("登录成功", userDTO);
    }

    /**
     * BCrypt加密
     *
     * @param password 密码
     * @return 密文
     */
    private String bcryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * BCrypt解密
     * @param password 密码
     * @param encodePassword 密文密码
     * @return 校验是否成功
     */
    private boolean decodeBCryptPassword(String password, String encodePassword) {
        return BCrypt.checkpw(password, encodePassword);
    }

    /**
     * 密码加密
     *
     * @param password 密码
     * @return 加密后密码
     */
    private String encryptPassword(String password, String salt) {
        String saltPassword = DigestUtils.md5Hex(password + salt);

        return saltPassword;
    }

    /**
     * 微信登录
     * @param code 登录态
     * @return 登录结果
     */
    @Override
    public ActionResult<Object> wxLogin(String code) {

        //发送appid+appsecret+code到wx服务器
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(SystemConstant.WX_LOGIN_AUTH_URL,
                String.class, appId, appSecret, code);

        WxLoginResponse wxLoginResponse = JSON.parseObject(responseEntity.getBody(), WxLoginResponse.class);

        if (StringUtils.hasText(wxLoginResponse.getErrcode())) {
            log.error("wx login failed, errmsg is [{}]", wxLoginResponse.getErrmsg());
            return ActionResult.getErrorResult("登录失败");
        }

        return ActionResult.getSuccessResult("登录成功", wxLoginResponse);
    }

    @Override
    public boolean checkWxUser(String rawData, String signature, String sessionKey) {

        String data = rawData + sessionKey;
        //用sha1算法计算出密文
        String sign = DigestUtils.sha1Hex(data);

        return sign.equals(signature);
    }

    @Override
    public WxUserDTO decryptWxUserInfo(String encryptedData, String iv, String sessionKey) throws Exception {

        String decrypt = decrypt(encryptedData, iv, sessionKey);

        return JSONObject.parseObject(decrypt, WxUserDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int registryWxUserInfo(WxUserDTO wxUserDTO) {
        UserDO findUser = userDOMapperExt.selectByOpenId(wxUserDTO.getOpenId());

        //已有该用户
        if (Objects.nonNull(findUser)) {
            //更新
            BeanUtils.copyProperties(wxUserDTO, findUser);
            findUser.setUpdateTime(new Date());
            userDOMapper.updateByPrimaryKeySelective(findUser);
            log.info("update wx user[{}] success", findUser.getUsername());
        } else {
            String username = RandomStringUtils.randomNumeric(11);
            //插入
            UserDO userDO = new UserDO();
            BeanUtils.copyProperties(wxUserDTO, userDO);
            userDO.setCreateTime(new Date());
            userDO.setUsername(username);
            userDO.setType(UserTypeEnum.WX_USER.getCode());

            userDOMapper.insertSelective(userDO);
            log.info("insert wx user success.");
        }

        return 1;
    }

    @Override
    public WxPhoneInfo getWxUserPhoneNumber(String code){
        WxAccessTokenResponse accessToken = getAccessToken();
        WxPhoneInfo wxPhoneInfo = restTemplate.postForEntity(SystemConstant.WX_PHONE_NUMBER_URL, code, WxPhoneInfo.class, accessToken.getAccess_token()).getBody();
//        System.out.println(wxPhoneInfo.getErrcode());
//        System.out.println(wxPhoneInfo.getErrmsg());
        if (StringUtils.hasText(wxPhoneInfo.getErrcode())) {
            log.error("get wx phone number failed, msg is {}", wxPhoneInfo.getErrmsg());
            throw new BusinessException(ErrorType.BIZ_ERROR, "get wx phone number failed");
        }

        return wxPhoneInfo;
    }

    /**
     * 获取微信小程序AccessToken
     * @return WxAccessTokenResponse
     */
    private WxAccessTokenResponse getAccessToken() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(SystemConstant.WX_ACCESS_TOKEN_URL,
                String.class, appId, appSecret);
        WxAccessTokenResponse wxAccessTokenResponse = JSON.parseObject(responseEntity.getBody(), WxAccessTokenResponse.class);

        if (StringUtils.hasText(wxAccessTokenResponse.getErrcode())) {
            log.error("request access token failed, msg [{}]", wxAccessTokenResponse.getErrmsg());
            throw new BusinessException(ErrorType.BIZ_ERROR, "get access token failed");
        }

        return wxAccessTokenResponse;
    }

    /**
     * 用户数据解密方法
     * @param encryptedData 密文
     * @param iv 加密算法的初始向量
     * @param sessionKey 用户登录session_key
     * @return 用户数据
     * @throws Exception
     */
    private String decrypt(String encryptedData, String iv, String sessionKey) throws Exception {

        byte[] dataBytes = Base64.decodeBase64(encryptedData);
        byte[] keyBytes = Base64.decodeBase64(sessionKey);
        byte[] ivBytes = Base64.decodeBase64(iv);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
        byte[] finalData = cipher.doFinal(dataBytes);

        return new String(finalData);
    }

    public static void main(String[] args) {
        String data = "{\"nickName\":\"Band\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"Guangzhou\",\"province\":\"Guangdong\",\"country\":\"CN\",\"avatarUrl\":\"http://wx.qlogo.cn/mmopen/vi_32/1vZvI39NWFQ9XM4LtQpFrQJ1xlgZxx3w7bQxKARol6503Iuswjjn6nIGBiaycAjAtpujxyzYsrztuuICqIM5ibXQ/0\"}HyVFkGl5F5OQWJZZaNzBBg==";
        byte[] bytes = DigestUtils.sha1(data);

        String s = DigestUtils.sha1Hex(data);

        System.out.println(s);

        String s1 = DigestUtils.md5Hex("xkjdkkjdndjknjknjakjnjkndsjnncklaj" + "djKJNnfjHJjk = nJKH");
        MessageDigest md5Digest = DigestUtils.getMd5Digest();

        System.out.println("s1="+s1);
    }

}

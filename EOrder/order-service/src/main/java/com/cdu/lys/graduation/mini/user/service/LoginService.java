package com.cdu.lys.graduation.mini.user.service;

import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.types.user.LoginQuery;
import com.cdu.lys.graduation.domain.user.dto.WxUserDTO;
import com.cdu.lys.graduation.domain.user.wx.WxPhoneInfo;

/**
 *
 *
 * @author liyinsong
 * @date 2022/1/8 16:37
 */
public interface LoginService {

    /**
     * 系统登录
     * @param loginQuery 登录参数
     * @return 结果
     */
    ActionResult<Object> systemLogin(LoginQuery loginQuery);

    /**
     * 微信登录
     * @param code 登录态
     * @return 微信授权接口返回信息
     */
    ActionResult<Object> wxLogin(String code);

    /**
     * 校验微信用户数据是否被修改
     * @param rawData 不包括敏感信息的原始数据字符串，用于计算签名
     * @param signature 签名
     * @param sessionKey 微信登录的session_key
     * @return 是否被修改
     */
    boolean checkWxUser(String rawData, String signature, String sessionKey);

    /**
     * 微信用户敏感数据解密
     *
     * @param encryptedData 密文
     * @param iv            加密算法的初始向量
     * @param sessionKey    用户登录session_key
     * @return 微信用户
     */
    WxUserDTO decryptWxUserInfo(String encryptedData, String iv, String sessionKey) throws Exception;

    /**
     * 注册微信用户
     * @param wxUserDTO 微信用户数据
     * @return 修改行数
     */
    int registryWxUserInfo(WxUserDTO wxUserDTO);

    /**
     * 获取微信手机号，个人用户无法获取
     * @param code 动态令牌
     * @return phone number
     */
    WxPhoneInfo getWxUserPhoneNumber(String code);
}

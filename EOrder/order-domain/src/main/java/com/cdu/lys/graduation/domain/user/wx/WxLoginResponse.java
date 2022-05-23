package com.cdu.lys.graduation.domain.user.wx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

/**
 * 微信登录返回信息
 *
 * @author liyinsong
 * @date 2022/2/21 20:00
 */
@Data
@ToString
public class WxLoginResponse extends WxResponseError {

    @JSONField(name = "openid")
    private String openid;

    @JSONField(name = "session_key")
    private String session_key;

    @JSONField(name = "unionid")
    private String unionid;

}

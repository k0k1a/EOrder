package com.cdu.lys.graduation.domain.user.wx;

import com.cdu.lys.graduation.domain.user.dto.UserDTO;
import lombok.Data;

/**
 * 微信登录接口返回数据
 *
 * @author liyinsong
 * @date 2022/1/14 19:17
 */
@Data
public class LoginUser extends UserDTO {

    private String openid;
    private String session_key;
    private String unionid;

}

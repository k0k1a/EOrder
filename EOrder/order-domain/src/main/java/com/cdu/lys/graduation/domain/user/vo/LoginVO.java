package com.cdu.lys.graduation.domain.user.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author liyinsong
 * @date 2022/2/22 14:32
 */
@Data
@ToString
public class LoginVO{
    private String token;
    private UserVO userInfo;
}

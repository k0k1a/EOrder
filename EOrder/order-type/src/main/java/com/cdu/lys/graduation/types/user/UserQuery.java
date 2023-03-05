package com.cdu.lys.graduation.types.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liyinsong
 * @date 2022/3/30 20:56
 */
@Data
public class UserQuery {

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String avatarUrl;

    @NotBlank(message = "昵称不能为空")
    private String nickname;
}

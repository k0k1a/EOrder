package com.cdu.lys.graduation.domain.user.vo;

import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/3/17 22:47
 */
@Data
public class AdminUserLoginVO {

    private String token;

    private String nickname;

    private String avatarUrl;
}

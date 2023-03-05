package com.cdu.lys.graduation.domain.user.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/2/24 14:13
 */
@Data
public class UserVO {

    @JsonProperty("nickName")
    private String nickname;

    private Integer gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String phone;
}

package com.cdu.lys.graduation.domain.user.dto;

import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/2/23 13:59
 */
@Data
public class UserDTO {

    private Integer id;

    private String username;

    private String nickname;

    private Integer gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String phone;

}

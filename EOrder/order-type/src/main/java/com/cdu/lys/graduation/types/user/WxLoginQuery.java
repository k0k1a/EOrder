package com.cdu.lys.graduation.types.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liyinsong
 * @date 2022/2/22 19:35
 */
@Data
public class WxLoginQuery {
    @NotBlank
    private String code;
}

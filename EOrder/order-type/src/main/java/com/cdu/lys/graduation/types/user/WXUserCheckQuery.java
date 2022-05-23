package com.cdu.lys.graduation.types.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liyinsong
 * @date 2022/1/16 16:01
 */
@Data
@ApiModel(description = "微信用户信息校验请求")
public class WXUserCheckQuery {

    @NotBlank
    private String rawData;
    @NotBlank
    private String signature;

}

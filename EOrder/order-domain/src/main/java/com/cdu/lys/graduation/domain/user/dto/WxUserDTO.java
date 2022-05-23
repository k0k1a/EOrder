package com.cdu.lys.graduation.domain.user.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/1/17 20:48
 */
@Data
public class WxUserDTO {

    private String openId;
    @JSONField(name = "nickName")
    private String nickname;
    private Integer gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private Watermark watermark;

    @Data
    class Watermark{
        private String timestamp;
        private String appid;
    }
}

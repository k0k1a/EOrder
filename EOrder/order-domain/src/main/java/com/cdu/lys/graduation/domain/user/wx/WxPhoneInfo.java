package com.cdu.lys.graduation.domain.user.wx;

import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/2/22 13:50
 */
@Data
public class WxPhoneInfo extends WxResponseError{
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private Watermark watermark;
}

@Data
class Watermark{
    private String appid;
    private String timestamp;
}


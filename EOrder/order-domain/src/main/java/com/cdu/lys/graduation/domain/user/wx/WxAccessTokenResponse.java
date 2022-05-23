package com.cdu.lys.graduation.domain.user.wx;

import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/2/22 11:16
 */
@Data
public class WxAccessTokenResponse extends WxResponseError{

    private String access_token;
    private String expires_in;
}

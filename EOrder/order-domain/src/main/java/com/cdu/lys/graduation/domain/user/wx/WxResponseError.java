package com.cdu.lys.graduation.domain.user.wx;

import lombok.Data;

/**
 * 微信接口返回数据错误
 *
 * @author liyinsong
 * @date 2022/2/21 19:56
 */
@Data
public class WxResponseError {
    /**
     * 错误码
     */
    private String errcode;

    /**
     * 错误信息
     */
    private String errmsg;
}

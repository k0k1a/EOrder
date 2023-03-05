package com.cdu.lys.graduation.types.user;

import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/2/24 16:46
 */
@Data
public class WxUserProfile {
    private String rawData;
    private String signature;
    private String encryptedData;
    private String iv;
}

package com.cdu.lys.graduation.types.coupon;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 优惠券的优惠群体枚举
 *
 * @author liyinsong
 * @date 2022/2/28 14:39
 */
public enum UserGroupEnum {

    ALL_USER("会员专享", 0),
    NEW_USER("新用户专享", 1),
    OLD_USER("老用户专享", 2);

    private String msg;
    private Integer code;

    UserGroupEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * 根据code获取msg
     * @param code code
     * @return msg
     */
    public static String getCodeMsg(Integer code) {

        for (UserGroupEnum value : UserGroupEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getMsg();
            }
        }
        return null;
    }

    public static String getMsgByCode(int code) {
        return Arrays.stream(UserGroupEnum.values()).
                filter(userGroupEnum -> userGroupEnum.getCode() == code)
                .collect(Collectors.toList()).get(0).getMsg();
    }
}

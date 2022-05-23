package com.cdu.lys.graduation.types.user;

/**
 * @author liyinsong
 * @date 2022/1/18 21:44
 */
public enum UserTypeEnum {

    GUEST(0, "游客"),
    WX_USER(1, "微信用户"),
    NATIVE_USER(2, "系统注册用户"),
    ADMIN_USER(3, "管理员"),;

    private Integer code;
    private String type;

    UserTypeEnum(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}

package com.cdu.lys.graduation.types.user;

/**
 * @author liyinsong
 * @date 2022/2/23 11:29
 */
public enum UserGenderEnum {
    UNKNOWN(0,"未知"),
    MALE(1,"男性"),
    FEMALE(2,"女性");

    private Integer code;
    private String gender;

    UserGenderEnum(int code, String gender) {
        this.code = code;
        this.gender = gender;
    }

    public Integer getCode() {
        return code;
    }

    public String getGender() {
        return gender;
    }
}

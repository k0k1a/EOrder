package com.cdu.lys.graduation.types;

/**
 * @author liyinsong
 * @date 2022/4/18 13:46
 */
public enum SwitchTypeEnum {

    ON("on"),
    OFF("off");

    private final String content;

    SwitchTypeEnum(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

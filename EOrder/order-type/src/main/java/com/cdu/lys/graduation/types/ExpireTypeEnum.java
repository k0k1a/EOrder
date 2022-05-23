package com.cdu.lys.graduation.types;

/**
 * @author liyinsong
 * @date 2022/3/2 10:21
 */
public enum ExpireTypeEnum {

    /**
     * 是
     */
    YES("y"),

    /**
     * 否
     */
    NO("n");

    private String type;

    ExpireTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

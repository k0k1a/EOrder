package com.cdu.lys.graduation.types.payment;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author liyinsong
 * @date 2022/3/7 16:19
 */
public enum DeliveryStatus {

    /**
     * 0,未配送；1,已配送
     */
    NOT_DELIVERED(0,"未配送"),
    DELIVERED(1,"已配送");

    private int code;
    private String msg;

    DeliveryStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsgByCode(int code) {
        return Arrays.stream(DeliveryStatus.values()).
                filter(deliveryStatus -> deliveryStatus.getCode() == code)
                .collect(Collectors.toList()).get(0).getMsg();
    }
}

package com.cdu.lys.graduation.types.payment;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author liyinsong
 * @date 2022/3/7 16:15
 */
public enum PayStatus {

    /**
     * 支付状态，0：未支付，1：已支付，3：线下付款，4：线下付款已收款，5：取消支付
     */
    UNPAID(0, "未支付"),
    PAID(1, "已支付"),
    OFFLINE_PAYMENT(3, "线下支付"),
    OFFLINE_PAID(4, "线下支付已收款"),
    CANCEL_PAID(5, "取消支付"),
    ;

    private int code;
    private String msg;

    PayStatus(int code, String msg) {
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
        return Arrays.stream(PayStatus.values()).
                filter(payStatus -> payStatus.getCode() == code)
                .collect(Collectors.toList()).get(0).getMsg();
    }
}

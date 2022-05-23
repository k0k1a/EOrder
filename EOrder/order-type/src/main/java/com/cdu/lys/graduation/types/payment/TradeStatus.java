package com.cdu.lys.graduation.types.payment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyinsong
 * @date 2022/3/7 16:09
 */
public enum TradeStatus {
    /**
     * 已生成订单，未付款
     */
    START(0, "待付款"),

    /**
     * 已付款，待配送
     */
    NOT_DELIVERY(1, "已收款"),

    /**
     * 已完成
     */
    FINISHED(2, "交易完成"),

    /**
     * 取消交易
     */
    CANCELLED(3, "取消交易"),

    /**
     * 订单未评论
     */
    NOT_COMMENT(4,"未评论");

    private final Integer code;
    private final String msg;

    TradeStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsgByCode(Integer code) {
        List<TradeStatus> collect = Arrays.stream(TradeStatus.values()).
                filter(tradeStatus -> tradeStatus.getCode().equals(code))
                .collect(Collectors.toList());
        return collect.get(0).getMsg();
    }
}

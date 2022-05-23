package com.cdu.lys.graduation.mini.payment;


import com.cdu.lys.graduation.domain.payment.Order;
import com.cdu.lys.graduation.domain.payment.PayInfo;

/**
 * 微信支付mock类
 *
 * @author liyinsong
 * @date 2022/3/7 14:17
 */
public class WeChatPayment {

    /**
     * 下单预支付
     * @param order 订单
     * @return 预支付信息
     */
    public static PayInfo preOrder(Order order){

        return new PayInfo(order);
    }

    /**
     * 支付mock方法
     * @param order 订单
     * @return 支付结果
     */
    public static boolean pay(PayInfo order){
        return true;
    }
}

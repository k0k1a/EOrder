package com.cdu.lys.graduation.domain.order;

import com.cdu.lys.graduation.domain.payment.OrderGoods;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 订单详细
 * @author liyinsong
 * @date 2022/3/9 17:09
 */
@Data
public class OrderDetail {

    private String orderNum;

    private Integer tradeStatus;

    private Date createTime;

    private Date expireTime;

    private String queueNum;

    private Double orderAmount;

    private Double payAmount;

    private Double couponAmount;

    private String remark;

    private Integer deliveryStatus;

    private String isComment;

    private List<OrderGoods> goodsList;
}

package com.cdu.lys.graduation.domain.admin.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/3/21 19:58
 */
@Data
public class OrderFormVO {
    private Integer id;

    private String username;

    private String orderNum;

    private String tradeStatus;

    private String payStatus;

    private Date createTime;

    private Date expireTime;

    private Date closeTime;

    private String remark;

    private Double orderAmount;

    private Double payAmount;

    private Double couponAmount;

    private Date payTime;

    private String outerTradeNo;

    private String queueNum;

    private String deliveryStatus;
}

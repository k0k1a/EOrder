package com.cdu.lys.graduation.domain.order.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/3/7 17:04
 */
@Data
public class OrderFormDTO {

    private String orderNum;

    private Integer tradeStatus;

    private Integer payStatus;

    private Date createTime;

    private Date closeTime;

    private String remark;

    private Double orderAmount;

    private Double payAmount;

}

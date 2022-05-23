package com.cdu.lys.graduation.types.payment;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/7 15:09
 */
@Data
public class PrePayQuery {

    /**
     * 用户优惠券id
     */
    private Integer userCouponId;

    /**
     * 订单商品
     */
    @Valid
    @NotNull(message = "商品不能为空")
    private List<CartGoods> cartGoodsList;

    /**
     * 预定时间 TODO
     */
    @Future
    private Date scheduledTime;

    /**
     * 备注
     */
    private String remark;

}

package com.cdu.lys.graduation.types.admin.coupon;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author liyinsong
 * @date 2022/3/21 22:03
 */
@Data
public class CouponUpdateQuery {

    @NotNull
    private Integer id;

    @NotNull
    private String couponName;

    @NotNull
    @Range(min = 0)
    private Integer number;

}

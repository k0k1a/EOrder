package com.cdu.lys.graduation.types.admin.coupon;

import com.cdu.lys.graduation.types.PageQuery;
import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/3/22 18:35
 */
@Data
public class CouponSearchQuery extends PageQuery {

    private String couponName;

    private Integer userType;
}

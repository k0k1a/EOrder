package com.cdu.lys.graduation.admin.coupon.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.repository.entity.CouponDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponSearchQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/20 17:00
 */
public interface AdminCouponService {

    PageResult<List<CouponDO>> getCouponList(PageQuery query);

    PageResult<List<CouponDO>> searchCoupon(CouponSearchQuery query);
}

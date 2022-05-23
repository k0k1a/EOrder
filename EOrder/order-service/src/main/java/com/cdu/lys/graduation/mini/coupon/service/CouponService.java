package com.cdu.lys.graduation.mini.coupon.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.coupon.dto.CouponDTO;
import com.cdu.lys.graduation.types.PageQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/28 14:50
 */
public interface CouponService {

    /**
     * 访客查询优惠券，返回新用户优惠券
     *
     * @return 优惠券
     */
    PageResult<List<CouponDTO>> getVisitorCoupons(PageQuery query);

    /**
     * 查询改用户能领取的优惠券
     *
     * @param query 分页查询
     * @param userId 用户id
     * @return 优惠券列表页
     */
    PageResult<List<CouponDTO>> getCoupons(PageQuery query, Integer userId);

    /**
     * 减少优惠券数量1
     *
     * @param couponId 优惠券id
     * @return 是否成功
     */
    boolean minusCouponNumber(Integer couponId);
}

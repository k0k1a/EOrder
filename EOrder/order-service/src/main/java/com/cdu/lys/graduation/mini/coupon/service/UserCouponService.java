package com.cdu.lys.graduation.mini.coupon.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.coupon.dto.CouponDTO;
import com.cdu.lys.graduation.domain.coupon.dto.UserCouponDTO;
import com.cdu.lys.graduation.types.PageQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/1 15:52
 */
public interface UserCouponService {

    /**
     * 领取优惠券
     * @param userId 用户id
     * @param couponDTO 优惠券
     * @return 是否成功
     */
    boolean getCoupon(Integer userId, CouponDTO couponDTO);

    /**
     * 获取用户有效的优惠券
     *
     * @param query 分页查询
     * @param userId 用户id
     * @return 优惠券
     */
    PageResult<List<UserCouponDTO>> getValidCoupons(PageQuery query, Integer userId);

    /**
     * 获取用户失效的优惠券
     * @param query 分页请求
     * @param userId 用户id
     * @return 优惠券
     */
    PageResult<List<UserCouponDTO>> getExpireCoupons(PageQuery query, Integer userId);

    /**
     * 用户使用优惠券
     *
     * @param id     用户优惠券id
     * @param userId 用户id
     * @param totalAmount 订单总价
     * @return 使用优惠券后的金额
     */
    Double useCoupon(Integer id, Integer userId, double totalAmount);
}

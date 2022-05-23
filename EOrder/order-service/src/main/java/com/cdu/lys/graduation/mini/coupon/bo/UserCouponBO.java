package com.cdu.lys.graduation.mini.coupon.bo;

import com.cdu.lys.graduation.repository.entity.UserCouponDO;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/13 18:29
 */
public interface UserCouponBO {

    /**
     * 查询用户失效的优惠券
     * @param userId 用户id
     * @return 优惠券
     */
    List<UserCouponDO> selectExpireCoupons(Integer userId);

    /**
     * 查询用户有效的优惠券
     * @param userId 用户id
     * @return 优惠券
     */
    List<UserCouponDO> selectValidCoupons(Integer userId);

    /**
     * 更新用户已失效的优惠券
     * @param userId 用户id
     * @return i
     */
    int updateExpiredCoupon(Integer userId);

    /**
     * 更新失效的优惠券数据
     * @return i
     */
    int updateExpiredCoupon();
}

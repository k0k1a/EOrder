package com.cdu.lys.graduation.mini.user.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.coupon.dto.UserCouponDTO;
import com.cdu.lys.graduation.domain.user.dto.UserDTO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.coupon.UserGroupEnum;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/28 16:11
 */
public interface UserService {

    UserDTO selectUserByUsername(String username);

    UserDTO selectUserByOpenId(String openId);

    /**
     * 获取用户类型
     * @param userId 用户id
     * @return 用户类型
     */
    UserGroupEnum getUserType(Integer userId);

    /**
     * 用户获取优惠券
     * @param userId 用户id
     * @param couponId 优惠券id
     * @return
     */
    boolean getCoupon(Integer userId, Integer couponId);

    /**
     * 获取用户能使用的优惠券
     * @param userId 用户id
     * @return 优惠券
     */
    PageResult<List<UserCouponDTO>> getAvailableCoupons(PageQuery query, Integer userId);

    /**
     * 获取用户失效的优惠券
     * @param userId 用户id
     * @param query 分页请求
     * @return 优惠券
     */
    PageResult<List<UserCouponDTO>> getExpireCoupons(PageQuery query, Integer userId);


}

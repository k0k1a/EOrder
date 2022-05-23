package com.cdu.lys.graduation.admin.coupon.bo;

import com.cdu.lys.graduation.repository.entity.CouponDO;
import com.cdu.lys.graduation.types.admin.coupon.CouponQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponSearchQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponUpdateQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/20 17:04
 */
public interface AdminCouponBO {

    List<CouponDO> selectCoupons();

    CouponDO getCouponById(Integer couponId);

    int updateCoupon(CouponUpdateQuery coupon);

    int deleteCoupon(Integer couponId);

    int addCoupon(CouponQuery query);

    List<CouponDO> search(CouponSearchQuery query);

    List<CouponDO> searchByUserType(Integer userType);

    List<CouponDO> searchByName(String couponName);
}

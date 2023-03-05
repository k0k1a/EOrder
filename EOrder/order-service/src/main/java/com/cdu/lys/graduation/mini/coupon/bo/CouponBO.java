package com.cdu.lys.graduation.mini.coupon.bo;

import com.cdu.lys.graduation.repository.entity.CouponDO;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/13 16:09
 */
public interface CouponBO {

    List<CouponDO> selectByUserTypes(Integer userType);

    List<CouponDO> selectByUserTypes(List<Integer> userTypes);

}

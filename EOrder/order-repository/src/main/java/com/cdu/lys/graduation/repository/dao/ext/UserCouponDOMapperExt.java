package com.cdu.lys.graduation.repository.dao.ext;

import com.cdu.lys.graduation.repository.entity.UserCouponDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/1 17:00
 */
public interface UserCouponDOMapperExt {

    /**
     * 根据userId和couponId查询
     *
     * @param userId   用户id
     * @param couponId 优惠券id
     * @return 用户优惠券DO
     */
    List<UserCouponDO> selectByUserIdAndCouponId(@Param("userId") Integer userId, @Param("couponId") Integer couponId);

    /**
     * 根据userId查询优惠券
     * @param userId 用户id
     * @return 优惠券
     */
    List<UserCouponDO> selectAllByUserId(Integer userId);

    void setCouponExpireById(@Param("id") Integer id, @Param("updateTime") Date updateTime);
}

package com.cdu.lys.graduation.mini.coupon.bo.impl;

import com.cdu.lys.graduation.mini.coupon.bo.UserCouponBO;
import com.cdu.lys.graduation.repository.dao.UserCouponDOMapper;
import com.cdu.lys.graduation.repository.entity.UserCouponDO;
import com.cdu.lys.graduation.repository.entity.UserCouponDOExample;
import com.cdu.lys.graduation.types.ExpireTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/13 18:29
 */
@Service
public class UserCouponBOImpl implements UserCouponBO {

    @Autowired
    private UserCouponDOMapper userCouponDOMapper;

    @Override
    public List<UserCouponDO> selectExpireCoupons(Integer userId) {
        UserCouponDOExample example = new UserCouponDOExample();
        example.setOrderByClause("update_time desc,id desc");
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andIsExpireEqualTo(ExpireTypeEnum.YES.getType())
                .andIsDeleteEqualTo("n");

        return userCouponDOMapper.selectByExample(example);
    }

    @Override
    public List<UserCouponDO> selectValidCoupons(Integer userId) {
        UserCouponDOExample example = new UserCouponDOExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andExpireTimeGreaterThanOrEqualTo(new Date())
                .andIsExpireEqualTo(ExpireTypeEnum.NO.getType())
                .andIsDeleteEqualTo("n");

        return userCouponDOMapper.selectByExample(example);
    }

    @Override
    public int updateExpiredCoupon(Integer userId){
        UserCouponDOExample example = new UserCouponDOExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andExpireTimeLessThan(new Date())
                .andIsDeleteEqualTo("n");

        UserCouponDO userCouponDO = new UserCouponDO();
        userCouponDO.setIsExpire(ExpireTypeEnum.YES.getType());
        userCouponDO.setUpdateTime(new Date());

        int i = userCouponDOMapper.updateByExampleSelective(userCouponDO, example);
        return i;
    }

    @Override
    public int updateExpiredCoupon() {
        UserCouponDOExample example = new UserCouponDOExample();
        example.createCriteria()
                .andExpireTimeLessThan(new Date())
                .andIsExpireEqualTo(ExpireTypeEnum.NO.getType())
                .andIsDeleteEqualTo("n");

        UserCouponDO userCouponDO = new UserCouponDO();
        userCouponDO.setIsExpire(ExpireTypeEnum.YES.getType());
        userCouponDO.setUpdateTime(new Date());

        int i = userCouponDOMapper.updateByExampleSelective(userCouponDO, example);
        return i;
    }

}

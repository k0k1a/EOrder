package com.cdu.lys.graduation.mini.coupon.bo.impl;

import com.cdu.lys.graduation.mini.coupon.bo.CouponBO;
import com.cdu.lys.graduation.repository.dao.CouponDOMapper;
import com.cdu.lys.graduation.repository.entity.CouponDO;
import com.cdu.lys.graduation.repository.entity.CouponDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/13 16:10
 */
@Service
public class CouponBOImpl implements CouponBO {

    @Autowired
    private CouponDOMapper couponDOMapper;

    @Override
    public List<CouponDO> selectByUserTypes(Integer userType) {
        CouponDOExample example = new CouponDOExample();
        example.createCriteria()
                .andDeadlineGreaterThan(new Date())
                .andUserTypeEqualTo(userType)
                .andIsDeleteEqualTo("n");
        return couponDOMapper.selectByExample(example);
    }

    @Override
    public List<CouponDO> selectByUserTypes(List<Integer> userTypes) {
        CouponDOExample example = new CouponDOExample();
        example.setOrderByClause("user_type desc,reach");
        example.createCriteria()
                .andDeadlineGreaterThan(new Date())
                .andUserTypeIn(userTypes)
                .andIsDeleteEqualTo("n");

        return couponDOMapper.selectByExample(example);
    }
}

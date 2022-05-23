package com.cdu.lys.graduation.admin.coupon.bo.impl;

import com.cdu.lys.graduation.admin.coupon.bo.AdminCouponBO;
import com.cdu.lys.graduation.repository.dao.CouponDOMapper;
import com.cdu.lys.graduation.repository.entity.CouponDO;
import com.cdu.lys.graduation.repository.entity.CouponDOExample;
import com.cdu.lys.graduation.types.admin.coupon.CouponQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponSearchQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponUpdateQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/20 17:04
 */
@Service
public class AdminCouponBOImpl implements AdminCouponBO {

    @Autowired
    private CouponDOMapper couponDOMapper;

    @Override
    public List<CouponDO> selectCoupons() {
        CouponDOExample example = new CouponDOExample();
        example.createCriteria()
                .andDeadlineGreaterThan(new Date())
                .andNumberGreaterThan(0)
                .andIsDeleteEqualTo("n");

        return couponDOMapper.selectByExample(example);
    }

    @Override
    public CouponDO getCouponById(Integer couponId) {
        CouponDOExample example = new CouponDOExample();
        example.createCriteria()
                .andIdEqualTo(couponId)
                .andIsDeleteEqualTo("n");

        return couponDOMapper.selectByExample(example).get(0);
    }

    @Override
    public int updateCoupon(CouponUpdateQuery coupon) {
        CouponDO couponDO = new CouponDO();
        BeanUtils.copyProperties(coupon, couponDO);
        couponDO.setUpdateTime(new Date());

        return couponDOMapper.updateByPrimaryKeySelective(couponDO);
    }

    @Override
    public int deleteCoupon(Integer couponId) {
        CouponDO couponDO = new CouponDO();
        couponDO.setId(couponId);
        couponDO.setUpdateTime(new Date());
        couponDO.setIsDelete("y");

        return couponDOMapper.updateByPrimaryKeySelective(couponDO);
    }

    @Override
    public int addCoupon(CouponQuery query) {
        CouponDO couponDO = new CouponDO();
        BeanUtils.copyProperties(query, couponDO);
        couponDO.setCreateTime(new Date());
        int i=couponDOMapper.insertSelective(couponDO);
        return i;
    }

    @Override
    public List<CouponDO> search(CouponSearchQuery query) {
        CouponDOExample example = new CouponDOExample();
        CouponDOExample.Criteria criteria = example.createCriteria();

        String couponName = query.getCouponName();
        Integer userType = query.getUserType();

        if (StringUtils.hasText(couponName)) {
            criteria.andCouponNameLike("%"+couponName+"%");
        }

        if (userType != null) {
            criteria.andUserTypeEqualTo(userType);
        }

        criteria.andDeadlineGreaterThan(new Date())
                .andNumberGreaterThan(0)
                .andIsDeleteEqualTo("n");

        return couponDOMapper.selectByExample(example);
    }

    @Override
    public List<CouponDO> searchByUserType(Integer userType) {
        CouponDOExample example = new CouponDOExample();
        example.createCriteria()
                .andUserTypeEqualTo(userType)
                .andDeadlineGreaterThan(new Date())
                .andNumberGreaterThan(0)
                .andIsDeleteEqualTo("n");

        return couponDOMapper.selectByExample(example);
    }

    @Override
    public List<CouponDO> searchByName(String couponName) {
        CouponDOExample example = new CouponDOExample();
        example.createCriteria()
                .andCouponNameEqualTo(couponName)
                .andDeadlineGreaterThan(new Date())
                .andNumberGreaterThan(0)
                .andIsDeleteEqualTo("n");

        return couponDOMapper.selectByExample(example);
    }

}

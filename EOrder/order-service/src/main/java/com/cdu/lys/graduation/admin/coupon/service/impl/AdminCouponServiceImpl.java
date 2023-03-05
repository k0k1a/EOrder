package com.cdu.lys.graduation.admin.coupon.service.impl;

import com.cdu.lys.graduation.admin.coupon.bo.AdminCouponBO;
import com.cdu.lys.graduation.admin.coupon.service.AdminCouponService;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.repository.entity.CouponDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponSearchQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/20 17:01
 */
@Service
public class AdminCouponServiceImpl implements AdminCouponService {

    @Autowired
    private AdminCouponBO adminCouponBO;

    @Override
    public PageResult<List<CouponDO>> getCouponList(PageQuery query) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());

        List<CouponDO> couponDOS = adminCouponBO.selectCoupons();

        PageResult<List<CouponDO>> pageResult = new PageResult<>(couponDOS);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Override
    public PageResult<List<CouponDO>> searchCoupon(CouponSearchQuery query) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());

        List<CouponDO> couponDOS = adminCouponBO.search(query);

        PageResult<List<CouponDO>> pageResult = new PageResult<>(couponDOS);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }
}

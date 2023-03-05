package com.cdu.lys.graduation.mini.coupon.service.impl;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.commons.utils.DateUtils;
import com.cdu.lys.graduation.mini.coupon.bo.UserCouponBO;
import com.cdu.lys.graduation.mini.coupon.service.UserCouponService;
import com.cdu.lys.graduation.domain.coupon.dto.CouponDTO;
import com.cdu.lys.graduation.domain.coupon.dto.UserCouponDTO;
import com.cdu.lys.graduation.repository.dao.UserCouponDOMapper;
import com.cdu.lys.graduation.repository.dao.ext.UserCouponDOMapperExt;
import com.cdu.lys.graduation.repository.entity.UserCouponDO;
import com.cdu.lys.graduation.types.ExpireTypeEnum;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.exception.BusinessException;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/1 15:52
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    private UserCouponDOMapper userCouponDOMapper;

    @Autowired
    private UserCouponDOMapperExt userCouponDOMapperExt;

    @Autowired
    private UserCouponBO userCouponBO;

    @Override
    public boolean getCoupon(Integer userId, CouponDTO couponDTO) {

        //判断是否已经领取
        List<UserCouponDO> userCouponDOS = userCouponDOMapperExt.selectByUserIdAndCouponId(userId, couponDTO.getId());
        if (!CollectionUtils.isEmpty(userCouponDOS)) {
            throw new BusinessException(ErrorType.PARAM_USER_AUTH, "不能重复领取哦");
        }

        UserCouponDO userCouponDO = new UserCouponDO();
        //计算失效时间
        Date expireTime = DateUtils.add(new Date(), couponDTO.getEffectiveTime());

        userCouponDO.setUserId(userId);
        userCouponDO.setCouponId(couponDTO.getId());
        userCouponDO.setCouponName(couponDTO.getCouponName());
        userCouponDO.setReach(couponDTO.getReach());
        userCouponDO.setReduce(couponDTO.getReduce());
        userCouponDO.setExpireTime(expireTime);
        userCouponDO.setCreateTime(new Date());

        userCouponDOMapper.insertSelective(userCouponDO);

        return true;
    }

    @Override
    public PageResult<List<UserCouponDTO>> getValidCoupons(PageQuery query, Integer userId) {
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<UserCouponDO> userCouponDOList = userCouponBO.selectValidCoupons(userId);

        List<UserCouponDTO> userCouponDTOS = convert2DTO(userCouponDOList);
        PageResult<List<UserCouponDTO>> pageResult = new PageResult<>(userCouponDTOS);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Override
    public PageResult<List<UserCouponDTO>> getExpireCoupons(PageQuery query, Integer userId) {
        //修改用户失效的优惠券
//        userCouponBO.updateExpiredCoupon(userId);
        Page<Object> page = PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<UserCouponDO> userCouponDOList = userCouponBO.selectExpireCoupons(userId);

        List<UserCouponDTO> userCouponDTOS = convert2DTO(userCouponDOList);
        PageResult<List<UserCouponDTO>> pageResult = new PageResult<>(userCouponDTOS);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Override
    public Double useCoupon(Integer id, Integer userId, double totalAmount) {
        UserCouponDO userCouponDO = userCouponDOMapper.selectByPrimaryKey(id);
        //用户使用不属于自己的优惠券
        if (!userId.equals(userCouponDO.getUserId())) {
            throw new BusinessException(ErrorType.BIZ_ERROR, "用户没有该优惠券");
        }

        //校验优惠券是否失效
        boolean isExpire = ExpireTypeEnum.YES.getType().equals(userCouponDO.getIsExpire());
        boolean isOutOfDate = userCouponDO.getExpireTime().before(new Date());
        boolean couponValid = isOutOfDate || isExpire;
        if (couponValid) {
            throw new BusinessException(ErrorType.BIZ_ERROR, "优惠券已失效");
        }

        if (userCouponDO.getReach() > totalAmount) {
            throw new BusinessException(ErrorType.BIZ_ERROR, "未达到优惠金额");
        }

        //设置优惠券失效
        userCouponDOMapperExt.setCouponExpireById(id, new Date());

        double resultAmount = totalAmount - userCouponDO.getReduce() < 0 ? 0 : totalAmount - userCouponDO.getReduce();
        return resultAmount;
    }

    /**
     * DO转为为DTO
     * @param userCouponDOS DO
     * @return DTO
     */
    private List<UserCouponDTO> convert2DTO(List<UserCouponDO> userCouponDOS){

        List<UserCouponDTO> available = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userCouponDOS)) {
            userCouponDOS.forEach(couponDO->{
                UserCouponDTO userCouponDTO = new UserCouponDTO();
                BeanUtils.copyProperties(couponDO, userCouponDTO);
                available.add(userCouponDTO);
            });
        }

        return available;
    }
}

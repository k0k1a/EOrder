package com.cdu.lys.graduation.controller.admin;

import com.cdu.lys.graduation.admin.coupon.bo.AdminCouponBO;
import com.cdu.lys.graduation.admin.coupon.service.AdminCouponService;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.admin.vo.CouponVO;
import com.cdu.lys.graduation.repository.entity.CouponDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponSearchQuery;
import com.cdu.lys.graduation.types.admin.coupon.CouponUpdateQuery;
import com.cdu.lys.graduation.types.coupon.UserGroupEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/21 21:56
 */
@RestController
@RequestMapping(value = "/eorder/admin")
@Api(tags = "管理系统优惠券接口")
@Slf4j
@Validated
public class AdminCouponController {

    @Autowired
    private AdminCouponService adminCouponService;

    @Autowired
    private AdminCouponBO adminCouponBO;

    @ApiOperation("优惠券列表")
    @PostMapping(value = "/coupon/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> getCouponList(@RequestBody @Valid PageQuery query) {
        PageResult<List<CouponDO>> pageResult = adminCouponService.getCouponList(query);

        List<CouponDO> data = pageResult.getData();
        List<CouponVO> couponVOS = new ArrayList<>();

        data.forEach(couponDO -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(couponDO, couponVO);
            couponVO.setUserType(UserGroupEnum.getMsgByCode(couponDO.getUserType()));
            couponVOS.add(couponVO);
        });
        PageResult<List<CouponVO>> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setData(couponVOS);
        log.info("Admin select coupon list success, pageNo:{}, pageSize:{}", query.getPageNo(), query.getPageSize());
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation("根据id获取优惠券")
    @GetMapping(value = "/coupon/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<CouponDO> getCouponById(Integer couponId) {
        CouponDO coupon = adminCouponBO.getCouponById(couponId);
        log.info("Admin select coupon success, id:{}", couponId);
        return ActionResult.getSuccessResult(coupon);
    }

    @ApiOperation("更新优惠券")
    @PostMapping(value = "/coupon/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Boolean> updateCoupon(@RequestBody @Valid CouponUpdateQuery couponUpdateQuery) {

        int i = adminCouponBO.updateCoupon(couponUpdateQuery);
        log.info("Admin update coupon success, rows:{}", i);
        return ActionResult.getSuccessResult("更新成功");
    }

    @ApiOperation("删除优惠券")
    @GetMapping(value = "/coupon/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> deleteCoupon(@NotNull Integer couponId) {
        int i = adminCouponBO.deleteCoupon(couponId);
        log.info("Admin delete coupon success, rows:{}", i);
        return ActionResult.getSuccessResult("删除成功");
    }

    @ApiOperation("添加优惠券")
    @PostMapping(value = "/coupon/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> addCoupon(@RequestBody @Valid CouponQuery query) {
        int i = adminCouponBO.addCoupon(query);
        log.info("Admin add coupon success, rows:{}", i);
        return ActionResult.getSuccessResult("添加成功");
    }

    @ApiOperation("搜索")
    @PostMapping(value = "/coupon/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<PageResult<List<CouponVO>>> searchCoupon(@RequestBody CouponSearchQuery query) {
        PageResult<List<CouponDO>> pageResult = adminCouponService.searchCoupon(query);

        List<CouponDO> data = pageResult.getData();
        List<CouponVO> couponVOS = new ArrayList<>();

        data.forEach(couponDO -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(couponDO, couponVO);
            couponVO.setUserType(UserGroupEnum.getMsgByCode(couponDO.getUserType()));
            couponVOS.add(couponVO);
        });
        PageResult<List<CouponVO>> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setData(couponVOS);

        log.info("Admin search coupon list success, pageNo:{}, pageSize:{}, couponName:{}, userType:{}", query.getPageNo(), query.getPageSize(), query.getCouponName(), query.getUserType());
        return ActionResult.getSuccessResult(result);
    }
}

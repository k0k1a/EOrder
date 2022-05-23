package com.cdu.lys.graduation.controller.coupon;

import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.mini.coupon.service.CouponService;
import com.cdu.lys.graduation.domain.coupon.dto.CouponDTO;
import com.cdu.lys.graduation.domain.coupon.vo.CouponVO;
import com.cdu.lys.graduation.domain.user.wx.LoginUser;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.types.PageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/28 14:21
 */
@RestController
@RequestMapping(value = "/eorder/app/coupon")
@Api(tags = "coupon接口")
@Slf4j
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private RedisService redisService;

    /**
     * 登录用户获取优惠券
     *
     * @param token 登录状态
     * @return 优惠券列表
     */
    @PostMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("查询优惠券")
    public ActionResult<PageResult<List<CouponVO>>> getCoupon(@RequestBody @Valid PageQuery query, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);
        Integer userId = loginValue.getId();

        PageResult<List<CouponDTO>> pageResult = couponService.getCoupons(query, userId);
        PageResult<List<CouponVO>> result = convertPageDTO2PageVO(pageResult);

        log.info("select coupons success");
        return ActionResult.getSuccessResult(result);
    }

    /**
     * 未登录用户获取优惠券
     *
     * @return 优惠券
     */
    @PostMapping(value = "/visit/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("访客查询优惠券")
    public ActionResult<Object> getVisitorCoupon(@RequestBody PageQuery query) {
        PageResult<List<CouponDTO>> pageResult = couponService.getVisitorCoupons(query);
        PageResult<List<CouponVO>> result = convertPageDTO2PageVO(pageResult);

        log.info("select visitor coupon success");
        return ActionResult.getSuccessResult(result);
    }


    /**
     * 将DTO转为VO
     * @param pageResult DTO
     * @return VO
     */
    private PageResult<List<CouponVO>> convertPageDTO2PageVO(PageResult<List<CouponDTO>> pageResult) {
        List<CouponDTO> couponDTOList = pageResult.getData();

        List<CouponVO> couponVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(couponDTOList)) {
            couponDTOList.forEach(couponDTO -> {
                CouponVO couponVO = new CouponVO();
                BeanUtils.copyProperties(couponDTO, couponVO);
                couponVOList.add(couponVO);
            });
        }

        PageResult<List<CouponVO>> result = new PageResult<>(couponVOList);
        BeanUtils.copyProperties(pageResult, result);
        result.setData(couponVOList);

        return result;
    }
}

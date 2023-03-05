package com.cdu.lys.graduation.controller.user;

import com.cdu.lys.graduation.admin.merchant.bo.MerchantBO;
import com.cdu.lys.graduation.admin.merchant.bo.MerchantRealPictureBO;
import com.cdu.lys.graduation.admin.merchant.convertor.MerchantConvertor;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.coupon.dto.UserCouponDTO;
import com.cdu.lys.graduation.domain.coupon.vo.UserCouponVO;
import com.cdu.lys.graduation.domain.order.OrderDetail;
import com.cdu.lys.graduation.domain.payment.Order;
import com.cdu.lys.graduation.domain.payment.PayInfo;
import com.cdu.lys.graduation.domain.user.vo.MerchantVO;
import com.cdu.lys.graduation.domain.user.wx.LoginUser;
import com.cdu.lys.graduation.mini.order.service.OrderService;
import com.cdu.lys.graduation.mini.payment.PaymentService;
import com.cdu.lys.graduation.mini.payment.WeChatPayment;
import com.cdu.lys.graduation.mini.user.service.UserService;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.repository.entity.MerchantDO;
import com.cdu.lys.graduation.repository.entity.MerchantRealPictureDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.payment.PayQuery;
import com.cdu.lys.graduation.types.payment.PrePayQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/1 14:35
 */
@RestController
@RequestMapping(value = "/eorder/app/user")
@Api(tags = "User操作接口")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MerchantBO merchantBO;

    @Autowired
    private MerchantRealPictureBO merchantRealPictureBO;

    @Autowired
    private MerchantConvertor convertor;

    /**
     * 用户领取优惠券
     *
     * @param couponId 优惠券
     * @param token    登录状态token
     * @return 领取结果
     */
    @GetMapping(value = "/coupon/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("用户领取优惠券")
    public ActionResult<Object> getCoupon(@NotNull(message = "优惠券id不能为空") Integer couponId, @RequestHeader("token") String token) {

        LoginUser loginValue = redisService.getLoginValue(token);
        Integer userId = loginValue.getId();

        boolean result = userService.getCoupon(userId, couponId);
        if (!result) {
            log.warn("user get coupon fail");
            return ActionResult.getErrorResult("领取失败");
        }
        log.info("user get coupon success");
        return ActionResult.getSuccessResult("领取成功");
    }

    /**
     * 获取用户有效的优惠券
     *
     * @param token 登录token
     * @return 查询结果
     */
    @PostMapping(value = "/coupon/valid", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("获取用户有效的优惠券")
    public ActionResult<PageResult<List<UserCouponVO>>> getAvailableCoupons(@RequestBody @Valid PageQuery query, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);
        Integer userId = loginValue.getId();

        PageResult<List<UserCouponDTO>> availableCoupons = userService.getAvailableCoupons(query, userId);
        PageResult<List<UserCouponVO>> result = convert2CouponPageVO(availableCoupons);

        log.info("select user's available coupons success");
        return ActionResult.getSuccessResult(result);
    }

    /**
     * 获取用户失效的优惠券
     *
     * @param token 登录状态
     * @return 查询结果
     */
    @PostMapping(value = "/coupon/expire", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("获取用户失效的优惠券")
    public ActionResult<PageResult<List<UserCouponVO>>> getExpireCoupons(@RequestBody @Valid PageQuery query, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);
        Integer userId = loginValue.getId();

        PageResult<List<UserCouponDTO>> pageResult = userService.getExpireCoupons(query, userId);
        PageResult<List<UserCouponVO>> result = convert2CouponPageVO(pageResult);

        log.info("select user's expire coupons success");
        return ActionResult.getSuccessResult(result);
    }

    /**
     * 预支付接口，生成订单
     *
     * @param prePayQuery 预支付请求
     * @param token       登录token
     * @return 订单
     */
    @PostMapping(value = "/order/pre/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("预支付接口")
    public ActionResult<PayInfo> prePay(@Valid @RequestBody PrePayQuery prePayQuery, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);
        Order order = paymentService.createOrder(prePayQuery, loginValue.getId());

        //调用微信支付mock接口
        PayInfo payInfo = WeChatPayment.preOrder(order);
        log.info("user prepaid success");
        return ActionResult.getSuccessResult(payInfo);
    }

    /**
     * 支付
     *
     * @param payQuery 支付请求
     * @param token    登录状态
     * @return 是否支付成功
     */
    @PostMapping(value = "/order/pay", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("支付")
    public ActionResult<Object> pay(@Valid @RequestBody PayQuery payQuery, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);

        boolean pay = paymentService.pay(payQuery, loginValue.getId());
        if (!pay) {
            log.info("user pay fail");
            return ActionResult.getErrorResult("支付失败");
        }
        log.info("user pay success");
        return ActionResult.getSuccessResult("支付成功");
    }

    /**
     * 获取订单详情
     *
     * @param orderNum 订单号
     * @param token    token
     * @return 订单详情
     */
    @GetMapping(value = "/order/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("获取订单详情")
    public ActionResult<OrderDetail> getOrderDetail(@NotBlank(message = "订单号不能为空") String orderNum, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);

        OrderDetail orderDetail = orderService.getOrderDetail(orderNum, loginValue.getId());
        log.info("get user orderDetail success");
        return ActionResult.getSuccessResult(orderDetail);
    }

    /**
     * 查询订单列表
     *
     * @param query page
     * @param token token
     * @return 分页数据
     */
    @PostMapping(value = "/order/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("查询用户订单列表")
    public ActionResult<PageResult<List<OrderDetail>>> getOrderList(@RequestBody @Valid PageQuery query, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);

        PageResult<List<OrderDetail>> orderList = orderService.getOrderList(query, loginValue.getId());
        log.info("select order list success");
        return ActionResult.getSuccessResult(orderList);
    }

    @ApiOperation("用户未评论订单")
    @PostMapping(value = "/order/noComment/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<PageResult<List<OrderDetail>>> getNoCommentsOrderList(@RequestBody @Valid PageQuery query, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);

        PageResult<List<OrderDetail>> orderList = orderService.getNoCommentsOrderList(query, loginValue.getId());
        log.info("select order list success");
        return ActionResult.getSuccessResult(orderList);
    }

    /**
     * 取消支付
     *
     * @param orderNum 订单号
     * @param token    token
     * @return 结果
     */
    @GetMapping(value = "/order/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("取消订单")
    public ActionResult<Object> cancelOrder(@NotBlank(message = "订单号不能为空") String orderNum, @RequestHeader("token") String token) {
        LoginUser loginValue = redisService.getLoginValue(token);
        boolean result = orderService.cancelOrder(orderNum, loginValue.getId());

        if (result) {
            log.info("cancel order success, orderNum:{}", orderNum);
            return ActionResult.getSuccessResult("取消成功");
        } else {
            log.info("cancel order failed, orderNum:{}", orderNum);
            return ActionResult.getErrorResult("取消失败");
        }
    }

    @ApiOperation("获取商户信息")
    @GetMapping(value = "/visit/merchant/get", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "Merchant:Info")
    public ActionResult<MerchantVO> getMerchantInfo() {
        MerchantDO merchantDO = merchantBO.get();
        MerchantVO target = new MerchantVO();

        List<MerchantRealPictureDO> merchantRealPictureDOS = merchantRealPictureBO.get();
        BeanUtils.copyProperties(merchantDO, target);
        target.setRealPictureUrl(convertor.convert2MerchantRealPictureVO(merchantRealPictureDOS));

        log.info("user get merchant info success");
        return ActionResult.getSuccessResult(target);
    }

    /**
     * 将pageDTO转为VO
     *
     * @param pageResult page
     * @return VO
     */
    private PageResult<List<UserCouponVO>> convert2CouponPageVO(PageResult<List<UserCouponDTO>> pageResult) {
        List<UserCouponDTO> expireCoupons = pageResult.getData();

        List<UserCouponVO> expire = new ArrayList<>();
        if (!CollectionUtils.isEmpty(expireCoupons)) {
            expireCoupons.forEach(expireCoupon -> {
                UserCouponVO userCouponVO = new UserCouponVO();
                BeanUtils.copyProperties(expireCoupon, userCouponVO);
                expire.add(userCouponVO);
            });
        }

        PageResult<List<UserCouponVO>> page = new PageResult<>(expire);
        BeanUtils.copyProperties(pageResult, page);
        page.setData(expire);

        return page;
    }

}

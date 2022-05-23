package com.cdu.lys.graduation.controller.admin;

import com.cdu.lys.graduation.admin.order.converter.OrderDataConverter;
import com.cdu.lys.graduation.admin.order.converter.OrderInfoDataConverter;
import com.cdu.lys.graduation.admin.order.bo.AdminOrderFormInfoBO;
import com.cdu.lys.graduation.admin.order.service.AdminOrderService;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.admin.vo.OrderFormInfoVO;
import com.cdu.lys.graduation.domain.admin.vo.OrderFormVO;
import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.order.OrderSearchQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/21 21:59
 */
@RestController
@RequestMapping(value = "/eorder/admin")
@Api(tags = "管理系统订单接口")
@Validated
@Slf4j
public class AdminOrderController {

    @Autowired
    private OrderDataConverter orderDataConverter;

    @Autowired
    private AdminOrderService adminOrderService;

    @Autowired
    private AdminOrderFormInfoBO adminOrderFormInfoBO;

    @Autowired
    private OrderInfoDataConverter orderInfoDataConverter;

    @ApiOperation("所有订单列表")
    @PostMapping(value = "/order/list/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> getOrderList(@RequestBody @Valid PageQuery query) {
        PageResult<List<OrderFormDO>> pageResult = adminOrderService.getAllOrders(query);

        PageResult<List<OrderFormVO>> result = orderDataConverter.convertOrderPageDO2VO(pageResult);

        log.info("Admin select order list success, pageNo:{}, pageSize:{}", query.getPageNo(), query.getPageSize());
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation("未完成订单")
    @PostMapping(value = "/order/list/undone", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<PageResult<List<OrderFormVO>>> getUndoneOrder(@RequestBody @Valid PageQuery query) {
        PageResult<List<OrderFormDO>> pageResult = adminOrderService.getUndoneOrders(query);

        PageResult<List<OrderFormVO>> result = orderDataConverter.convertOrderPageDO2VO(pageResult);

        log.info("Admin select order list success, pageNo:{}, pageSize:{}", query.getPageNo(), query.getPageSize());
        return ActionResult.getSuccessResult(result);
    }

    @ApiOperation("订单详情")
    @GetMapping(value = "/order/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> getOrderDetailByOrderNum(@NotBlank String orderNum) {
        List<OrderFormInfoDO> orderFormInfoDOS = adminOrderFormInfoBO.getByOrderNum(orderNum);
        List<OrderFormInfoVO> orderFormInfoVOS = orderInfoDataConverter.convertOrderInfoDO2VO(orderFormInfoDOS);

        log.info("Admin select order detail success, orderNum:{}", orderNum);
        return ActionResult.getSuccessResult(orderFormInfoVOS);
    }

    @ApiOperation("配送单个商品")
    @GetMapping(value = "/order/delivery/one", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> deliveryOne(@NotNull Integer orderInfoId) {
        boolean res = adminOrderService.deliveryOne(orderInfoId);
        if (!res) {
            log.info("Admin delivery one failed, orderFormInfoId:{}", orderInfoId);
            return ActionResult.getErrorResult("配送失败");
        }
        log.info("Admin delivery one success, orderFormInfoId:{}", orderInfoId);
        return ActionResult.getSuccessResult("配送成功");
    }

    @ApiOperation("配送所有商品")
    @GetMapping(value = "/order/delivery/all")
    public ActionResult<Object> deliveryAll(@NotBlank(message = "订单号不能为空") String orderNum) {
        boolean res = adminOrderService.deliveryAll(orderNum);
        if (!res) {
            log.info("Admin delivery order failed, orderNum:{}", orderNum);
            return ActionResult.getErrorResult("配送失败");
        }
        log.info("Admin delivery order success, orderNum:{}", orderNum);
        return ActionResult.getSuccessResult("配送成功");
    }

    @ApiOperation("搜索")
    @PostMapping(value = "/order/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ActionResult<Object> search(@RequestBody @Valid OrderSearchQuery query) {
        PageResult<List<OrderFormDO>> pageResult = adminOrderService.search(query);

        PageResult<List<OrderFormVO>> result = orderDataConverter.convertOrderPageDO2VO(pageResult);

        log.info("Admin search order list success, pageNo:{}, pageSize:{}", query.getPageNo(), query.getPageSize());
        return ActionResult.getSuccessResult(result);
    }
}

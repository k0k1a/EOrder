package com.cdu.lys.graduation.mini.order.service.impl;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.order.OrderDetail;
import com.cdu.lys.graduation.domain.payment.OrderGoods;
import com.cdu.lys.graduation.mini.order.bo.OrderFormBO;
import com.cdu.lys.graduation.mini.order.bo.OrderFormInfoBO;
import com.cdu.lys.graduation.mini.order.service.OrderService;
import com.cdu.lys.graduation.repository.dao.ext.GoodsMapperExt;
import com.cdu.lys.graduation.repository.dao.ext.OrderFormDOMapperExt;
import com.cdu.lys.graduation.repository.dao.ext.OrderFormInfoDOMapperExt;
import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;
import com.cdu.lys.graduation.repository.entity.ext.OrderDetailDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.exception.BusinessException;
import com.cdu.lys.graduation.types.exception.ErrorType;
import com.cdu.lys.graduation.types.payment.PayStatus;
import com.cdu.lys.graduation.types.payment.TradeStatus;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liyinsong
 * @date 2022/3/9 16:49
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderFormDOMapperExt orderFormDOMapperExt;

    @Autowired
    private OrderFormInfoDOMapperExt orderFormInfoDOMapperExt;

    @Autowired
    private OrderFormBO orderFormBO;

    @Autowired
    private OrderFormInfoBO orderFormInfoBO;

    @Autowired
    private GoodsMapperExt goodsMapperExt;

    @Override
    public OrderDetail getOrderDetail(String orderNum, Integer userId) {
        OrderFormDO orderFormDO = orderFormBO.selectByOrderNum(orderNum);
        if (!userId.equals(orderFormDO.getUserId())) {
            throw new BusinessException(ErrorType.BIZ_ERROR, "系统错误");
        }

        //订单已失效
        Date expireTime = orderFormDO.getExpireTime();
        if (orderFormDO.getPayStatus().equals(PayStatus.UNPAID.getCode())
                && Objects.nonNull(expireTime) && expireTime.before(new Date())) {
            this.cancelOrder(orderNum, userId);
            orderFormDO = orderFormBO.selectByOrderNum(orderNum);
        }

        List<OrderFormInfoDO> orderFormInfoDOList = orderFormInfoDOMapperExt.selectByOrderNum(orderNum);
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(orderFormDO, orderDetail);
        List<OrderGoods> goodsList = convertOrderFormInfoDO2OrderGoods(orderFormInfoDOList);
        orderDetail.setGoodsList(goodsList);

        return orderDetail;
    }

    @Override
    public PageResult<List<OrderDetail>> getOrderList(PageQuery pageQuery, Integer userId) {
        Page<Object> page = PageHelper.startPage(pageQuery.getPageNo(), pageQuery.getPageSize());
        List<OrderDetailDO> orderDetailDOList = orderFormDOMapperExt.selectOrderDetailList(userId);

        List<OrderDetail> orderDetails = convertDetailDOList2OrderDetail(orderDetailDOList);

        PageResult<List<OrderDetail>> pageResult = new PageResult<>(orderDetails);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Override
    public PageResult<List<OrderDetail>> getNoCommentsOrderList(PageQuery pageQuery, Integer userId) {
        Page<Object> page = PageHelper.startPage(pageQuery.getPageNo(), pageQuery.getPageSize());
        List<OrderDetailDO> orderDetailDOList = orderFormDOMapperExt.selectUserNoCommentsOrderList(userId);

        List<OrderDetail> orderDetails = convertDetailDOList2OrderDetail(orderDetailDOList);

        PageResult<List<OrderDetail>> pageResult = new PageResult<>(orderDetails);
        pageResult.setPageNum(page.getPageNum());
        pageResult.setPageSize(page.getPageSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean cancelOrder(String orderNum, Integer userId) {
        OrderFormDO selectedForm = orderFormBO.selectByOrderNum(orderNum);
        if (!userId.equals(selectedForm.getUserId())) {
            throw new BusinessException(ErrorType.PARAM_ERROR, "非法参数");
        }

        //订单已完成
        if (selectedForm.getTradeStatus().equals(TradeStatus.FINISHED.getCode())) {
            throw new BusinessException(ErrorType.BIZ_ERROR, "订单已完成");
        }
        //订单已取消
        if (selectedForm.getTradeStatus().equals(TradeStatus.CANCELLED.getCode())) {
            return false;
        }

        //修改订单状态为已取消
        OrderFormDO orderFormDO = new OrderFormDO();
        orderFormDO.setTradeStatus(TradeStatus.CANCELLED.getCode());
        orderFormDO.setCloseTime(new Date());

        orderFormBO.updateByOrderNumSelective(orderFormDO, orderNum);

        List<OrderFormInfoDO> orderFormInfoDOList = orderFormInfoBO.selectByOrderNum(orderNum);

        orderFormInfoDOList.forEach(orderFormInfoDO -> {
            Integer goodsId = orderFormInfoDO.getGoodsId();
            Integer quantity = orderFormInfoDO.getGoodsQuantity();

            //加回商品库存
            goodsMapperExt.increaseGoodsStockById(goodsId, quantity);
        });

        return true;
    }


    /**
     * 转为OrderGoods
     * @param orderFormInfoDOList DO
     * @return OrderGoods
     */
    private List<OrderGoods> convertOrderFormInfoDO2OrderGoods(List<OrderFormInfoDO> orderFormInfoDOList) {
        List<OrderGoods> goodsList = new ArrayList<>();
        orderFormInfoDOList.forEach(orderFormInfoDO -> {
            OrderGoods orderGoods = new OrderGoods();
            BeanUtils.copyProperties(orderFormInfoDO, orderGoods);
            goodsList.add(orderGoods);
        });
        return goodsList;
    }

    /**
     * 订单列表DO转换
     * @param orderDetailDOList 订单列表DO
     * @return
     */
    private List<OrderDetail> convertDetailDOList2OrderDetail(List<OrderDetailDO> orderDetailDOList) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetailDOList.forEach(orderDetailDO -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(orderDetailDO, orderDetail);
            List<OrderGoods> goodsList = convertOrderFormInfoDO2OrderGoods(orderDetailDO.getOrderFormInfoDOList());
            orderDetail.setGoodsList(goodsList);
            orderDetails.add(orderDetail);
        });
        return orderDetails;
    }

}

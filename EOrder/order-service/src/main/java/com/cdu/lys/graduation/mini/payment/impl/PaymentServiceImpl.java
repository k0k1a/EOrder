package com.cdu.lys.graduation.mini.payment.impl;

import com.cdu.lys.graduation.commons.utils.DateUtils;
import com.cdu.lys.graduation.domain.goods.dto.GoodsDTO;
import com.cdu.lys.graduation.domain.payment.Order;
import com.cdu.lys.graduation.domain.payment.OrderGoods;
import com.cdu.lys.graduation.mini.coupon.service.UserCouponService;
import com.cdu.lys.graduation.mini.goods.service.GoodsService;
import com.cdu.lys.graduation.mini.order.bo.OrderFormBO;
import com.cdu.lys.graduation.mini.order.service.OrderService;
import com.cdu.lys.graduation.mini.payment.PaymentService;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.repository.dao.GoodsOptionItemDOMapper;
import com.cdu.lys.graduation.repository.dao.OrderFormDOMapper;
import com.cdu.lys.graduation.repository.dao.OrderFormInfoDOMapper;
import com.cdu.lys.graduation.repository.entity.GoodsOptionItemDO;
import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;
import com.cdu.lys.graduation.types.payment.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liyinsong
 * @date 2022/3/7 14:58
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderFormDOMapper orderFormDOMapper;

    @Autowired
    private OrderFormBO orderFormBO;

    @Autowired
    private OrderFormInfoDOMapper orderFormInfoDOMapper;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsOptionItemDOMapper goodsOptionItemDOMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderService orderService;

    private static Random rand;

    static {
        try {
            rand = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createOrder(PrePayQuery prePayQuery, Integer userId) {

        List<OrderGoods> orderGoodsList = this.getOrderGoods(prePayQuery.getCartGoodsList());
        double orderAmount = this.sumTotalPrice(orderGoodsList);

        Integer couponId = prePayQuery.getUserCouponId();

        Double payAmount = orderAmount;
        //使用优惠券,返回优惠后的金额
        if (couponId != null) {
            payAmount = userCouponService.useCoupon(couponId, userId, orderAmount);
        }
        double couponAmount = orderAmount - payAmount;

        Date createTime = new Date();
        //生成订单号
        String orderNum = this.createOrderNum();
        OrderFormDO orderFormDO = new OrderFormDO();
        orderFormDO.setUserId(userId);
        orderFormDO.setUserCouponId(couponId);
        orderFormDO.setOrderNum(orderNum);
        orderFormDO.setTradeStatus(TradeStatus.START.getCode());
        orderFormDO.setPayStatus(PayStatus.UNPAID.getCode());
        orderFormDO.setCreateTime(createTime);
        orderFormDO.setExpireTime(DateUtils.add(orderFormDO.getCreateTime(), Calendar.MINUTE, 30));
        orderFormDO.setRemark(prePayQuery.getRemark());
        orderFormDO.setOrderAmount(orderAmount);
        orderFormDO.setPayAmount(payAmount);
        orderFormDO.setCouponAmount(couponAmount);
        orderFormDO.setDeliveryStatus(DeliveryStatus.NOT_DELIVERED.getCode());

        //插入订单信息
        orderFormDOMapper.insertSelective(orderFormDO);

        //插入订单商品信息order_info表

        orderGoodsList.forEach(orderGoods -> {
            OrderFormInfoDO orderFormInfoDO = new OrderFormInfoDO();
            orderFormInfoDO.setOrderNum(orderNum);
            orderFormInfoDO.setCreateTime(createTime);
            orderFormInfoDO.setUpdateTime(createTime);
            BeanUtils.copyProperties(orderGoods, orderFormInfoDO);

            //购买商品
            goodsService.buyGoodsById(orderGoods.getGoodsId(), orderGoods.getGoodsQuantity());
            //插入订单详情表
            orderFormInfoDOMapper.insertSelective(orderFormInfoDO);
        });

        Order order = new Order();
        order.setOrderNum(orderNum);
        order.setOrderAmount(orderAmount);
        order.setPayAmount(payAmount);

        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean pay(PayQuery payQuery, Integer userId) {
        String orderNum = payQuery.getOrderNum();
        OrderFormDO orderFormDO = orderFormBO.selectByOrderNum(orderNum);
        //已经支付
        if (orderFormDO.getPayStatus().equals(PayStatus.PAID.getCode())) {
            return false;
        }
        //用户点击取消支付
        if (!payQuery.isPaid()) {
            orderFormDO.setPayStatus(PayStatus.CANCEL_PAID.getCode());
            orderFormDOMapper.updateByPrimaryKeySelective(orderFormDO);
            return false;
        }

        //订单已失效
        Date expireTime = orderFormDO.getExpireTime();
        if (Objects.nonNull(expireTime) && expireTime.before(new Date())) {
            orderService.cancelOrder(orderNum, userId);
            return false;
        }
        orderFormDO.setQueueNum(this.getQueueNumber());
        orderFormDO.setPayTime(new Date());
        orderFormDO.setPayStatus(PayStatus.PAID.getCode());
        orderFormDO.setTradeStatus(TradeStatus.NOT_DELIVERY.getCode());
        orderFormDOMapper.updateByPrimaryKeySelective(orderFormDO);
        redisService.incrDailyIncome(orderFormDO.getOrderAmount());
        return true;
    }

    /**
     * 生成20位订单号
     * @return 订单号
     */
    private String createOrderNum() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = sdf.format(new Date());

        int i = rand.nextInt(900000) + 100000;
        return dateString+i;
    }

    /**
     * 获取排队号
     * @return 排队号
     */
    private String getQueueNumber(){
        int dailySales = redisService.getDailySales();

        String res = String.format("%03d", dailySales);
        return "A" + res;
    }

    /**
     * 将购物车商品类转换为系统订单商品类
     * @param cartGoods 购物车请求商品
     * @return 订单商品
     */
    private List<OrderGoods> getOrderGoods(List<CartGoods> cartGoods) {
        List<OrderGoods> orderGoodsList = new ArrayList<>();

        cartGoods.forEach(cart -> {
            GoodsDTO goodsDTO = goodsService.selectGoodsById(cart.getGoodsId());
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setGoodsId(goodsDTO.getId());
            orderGoods.setGoodsName(goodsDTO.getGoodsName());
            if (!CollectionUtils.isEmpty(goodsDTO.getPictures())) {
                orderGoods.setGoodsPic(goodsDTO.getPictures().get(0).getPicUrl());
            }
            orderGoods.setGoodsPrice(goodsDTO.getPrice());
            orderGoods.setGoodsQuantity(cart.getQuantity());

            this.setExtra(orderGoods, cart.getOptionIds());

            orderGoodsList.add(orderGoods);
        });

        return orderGoodsList;
    }


    /**
     * 设置额外选择的
     * @param orderGoods 订单商品
     * @param optionIds 选择的项id集合
     */
    private void setExtra(OrderGoods orderGoods, List<Integer> optionIds) {

        if (CollectionUtils.isEmpty(optionIds)) {
            return;
        }
        StringBuilder extraText = new StringBuilder();
        double extraPrice=0;

        for (Integer id : optionIds) {
            GoodsOptionItemDO goodsOptionItemDO = goodsOptionItemDOMapper.selectByPrimaryKey(id);
            extraText.append(goodsOptionItemDO.getOptionItem());
            extraText.append("+");
            extraPrice += goodsOptionItemDO.getExtraPrice();
        }
        orderGoods.setExtraPrice(extraPrice);
        String options = extraText.substring(0, extraText.length() - 1);
        orderGoods.setExtraOptions(options);
    }

    /**
     * 计算总价
     * @param goodsList 订单商品集合
     * @return 总价
     */
    private double sumTotalPrice(List<OrderGoods> goodsList){
        double total=0;

        for (OrderGoods goods : goodsList) {
            double curPrice = (goods.getGoodsPrice() + goods.getExtraPrice()) * goods.getGoodsQuantity();

            total += curPrice;
        }

        return total;
    }
}

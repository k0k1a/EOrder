package com.cdu.lys.graduation.admin.order.bo.impl;

import com.cdu.lys.graduation.admin.order.bo.AdminOrderBO;
import com.cdu.lys.graduation.mini.user.bo.UserBO;
import com.cdu.lys.graduation.repository.dao.OrderFormDOMapper;
import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.repository.entity.OrderFormDOExample;
import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.order.OrderSearchQuery;
import com.cdu.lys.graduation.types.payment.TradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liyinsong
 * @date 2022/3/20 17:39
 */
@Service
public class AdminOrderBOImpl implements AdminOrderBO {

    @Autowired
    private OrderFormDOMapper orderFormDOMapper;

    @Autowired
    private UserBO userBO;

    @Override
    public List<OrderFormDO> getAllOrders(PageQuery query) {
        OrderFormDOExample example = new OrderFormDOExample();
        example.setOrderByClause("create_time desc,id desc");
        example.createCriteria();

        return orderFormDOMapper.selectByExample(example);
    }

    @Override
    public List<OrderFormDO> getUndoneOrders(PageQuery query) {
        OrderFormDOExample example = new OrderFormDOExample();
        //按照创建时间（天）倒序，排队号升序
        example.setOrderByClause("DATE_FORMAT(create_time,'%y-%m-%d') DESC, queue_num");
        example.createCriteria()
                .andTradeStatusEqualTo(TradeStatus.NOT_DELIVERY.getCode());

        return orderFormDOMapper.selectByExample(example);
    }

    @Override
    public int updateOrderFormByOrderNum(String orderNum, OrderFormDO orderFormDO) {
        OrderFormDOExample example = new OrderFormDOExample();
        example.createCriteria()
                .andOrderNumEqualTo(orderNum);

        return orderFormDOMapper.updateByExampleSelective(orderFormDO, example);
    }

    @Override
    public List<OrderFormDO> getByOrderNum(String orderNum) {
        OrderFormDOExample example = new OrderFormDOExample();
        example.createCriteria()
                .andOrderNumEqualTo(orderNum);

        return orderFormDOMapper.selectByExample(example);
    }

    @Override
    public List<OrderFormDO> search(OrderSearchQuery query) {
        OrderFormDOExample example = new OrderFormDOExample();
        example.setOrderByClause("create_time desc,id desc");
        OrderFormDOExample.Criteria criteria = example.createCriteria();

        //精确搜索订单号
        if (StringUtils.hasLength(query.getOrderNum())) {
            criteria.andOrderNumEqualTo(query.getOrderNum());
            return orderFormDOMapper.selectByExample(example);
        }

        //根据用户名查找
        if (StringUtils.hasLength(query.getUsername())) {
            UserDO userDO = userBO.getUserByUsername(query.getUsername());
            if (userDO != null) {
                criteria.andUserIdEqualTo(userDO.getId());
            }
        }

        //交易状态
        Integer tradeStatus = query.getTradeStatus();
        if (query.getIsUndone().equals(true)) {
            criteria.andTradeStatusEqualTo(TradeStatus.NOT_DELIVERY.getCode());
        }else if (Objects.nonNull(tradeStatus)) {
            criteria.andTradeStatusEqualTo(tradeStatus);
        }

        if (StringUtils.hasLength(query.getQueueNum())) {
            criteria.andQueueNumEqualTo(query.getQueueNum());
        }

        if (Objects.nonNull(query.getDeliveryStatus())) {
            criteria.andDeliveryStatusEqualTo(query.getDeliveryStatus());
        }

        return orderFormDOMapper.selectByExample(example);
    }

    @Override
    public List<OrderFormDO> getOrderBeforeDate(Date date) {
        OrderFormDOExample example = new OrderFormDOExample();
        example.createCriteria()
                .andCreateTimeGreaterThanOrEqualTo(date);

        return orderFormDOMapper.selectByExample(example);
    }
}

package com.cdu.lys.graduation.mini.order.bo.impl;

import com.cdu.lys.graduation.mini.order.bo.OrderFormBO;
import com.cdu.lys.graduation.repository.dao.OrderFormDOMapper;
import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.repository.entity.OrderFormDOExample;
import com.cdu.lys.graduation.types.payment.TradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/11 17:16
 */
@Service
public class OrderFormBOImpl implements OrderFormBO {

    @Autowired
    private OrderFormDOMapper orderFormDOMapper;

    @Override
    public OrderFormDO selectByOrderNum(String orderNum) {
        OrderFormDOExample example = new OrderFormDOExample();
        example.createCriteria()
                .andOrderNumEqualTo(orderNum);
        List<OrderFormDO> orderFormDOList = orderFormDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(orderFormDOList)) {
            return null;
        }
        return orderFormDOList.get(0);
    }

    @Override
    public int updateByOrderNumSelective(OrderFormDO orderFormDO, String orderNum) {

        OrderFormDOExample orderFormDOExample = new OrderFormDOExample();
        orderFormDOExample.createCriteria()
                .andOrderNumEqualTo(orderNum);

        int i = orderFormDOMapper.updateByExampleSelective(orderFormDO, orderFormDOExample);
        return i;
    }

    @Override
    public List<OrderFormDO> selectExpiredOrderForm() {
        OrderFormDOExample example = new OrderFormDOExample();
        example.createCriteria()
                .andTradeStatusEqualTo(TradeStatus.START.getCode())
                .andExpireTimeLessThan(new Date());

        return orderFormDOMapper.selectByExample(example);
    }
}

package com.cdu.lys.graduation.mini.order.bo.impl;

import com.cdu.lys.graduation.mini.order.bo.OrderFormInfoBO;
import com.cdu.lys.graduation.repository.dao.OrderFormInfoDOMapper;
import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;
import com.cdu.lys.graduation.repository.entity.OrderFormInfoDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/11 17:32
 */
@Service
public class OrderFormInfoBOImpl implements OrderFormInfoBO {
    @Autowired
    private OrderFormInfoDOMapper orderFormInfoDOMapper;

    @Override
    public List<OrderFormInfoDO> selectByOrderNum(String orderNum) {
        OrderFormInfoDOExample orderFormInfoDOExample = new OrderFormInfoDOExample();
        orderFormInfoDOExample.createCriteria()
                .andOrderNumEqualTo(orderNum);

        List<OrderFormInfoDO> orderFormInfoDOList = orderFormInfoDOMapper.selectByExample(orderFormInfoDOExample);

        return orderFormInfoDOList;
    }
}

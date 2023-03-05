package com.cdu.lys.graduation.mini.order.bo;

import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;

import java.util.List;

/**
 * 订单详情表BO
 *
 * @author liyinsong
 * @date 2022/3/11 17:32
 */
public interface OrderFormInfoBO {

    /**
     * 根据订单号查询订单详情
     * @param orderNum 订单号
     * @return 订单详情
     */
    List<OrderFormInfoDO> selectByOrderNum(String orderNum);
}

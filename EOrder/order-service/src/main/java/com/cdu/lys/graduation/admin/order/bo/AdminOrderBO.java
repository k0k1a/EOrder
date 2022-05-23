package com.cdu.lys.graduation.admin.order.bo;

import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.order.OrderSearchQuery;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/20 17:39
 */
public interface AdminOrderBO {

    List<OrderFormDO> getAllOrders(PageQuery query);

    List<OrderFormDO> getUndoneOrders(PageQuery query);

    int updateOrderFormByOrderNum(String orderNum, OrderFormDO orderFormDO);

    List<OrderFormDO> getByOrderNum(String orderNum);

    List<OrderFormDO> search(OrderSearchQuery query);

    /**
     * 指定时间前的订单
     * @param date 时间
     * @return 订单list
     */
    List<OrderFormDO> getOrderBeforeDate(Date date);
}

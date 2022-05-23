package com.cdu.lys.graduation.admin.order.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.order.OrderSearchQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/20 17:38
 */
public interface AdminOrderService {

    PageResult<List<OrderFormDO>> getAllOrders(PageQuery query);

    PageResult<List<OrderFormDO>> getUndoneOrders(PageQuery query);

    boolean deliveryOne(Integer orderInfoId);

    boolean deliveryAll(String orderNum);

    PageResult<List<OrderFormDO>> search(OrderSearchQuery query);
}

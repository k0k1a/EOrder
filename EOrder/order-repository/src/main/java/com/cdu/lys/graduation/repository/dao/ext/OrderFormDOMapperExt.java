package com.cdu.lys.graduation.repository.dao.ext;

import com.cdu.lys.graduation.repository.entity.ext.OrderDetailDO;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/8 09:56
 */
public interface OrderFormDOMapperExt {

    /**
     * 查询用户订单列表
     * @param userId 用户id
     * @return 订单列表
     */
    List<OrderDetailDO> selectOrderDetailList(Integer userId);

    List<OrderDetailDO> selectUserNoCommentsOrderList(Integer userId);
}

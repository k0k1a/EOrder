package com.cdu.lys.graduation.repository.entity.ext;

import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/10 15:23
 */
public class OrderDetailDO extends OrderFormDO {
    private List<OrderFormInfoDO> orderFormInfoDOList;

    public List<OrderFormInfoDO> getOrderFormInfoDOList() {
        return orderFormInfoDOList;
    }

    public void setOrderFormInfoDOList(List<OrderFormInfoDO> orderFormInfoDOList) {
        this.orderFormInfoDOList = orderFormInfoDOList;
    }
}

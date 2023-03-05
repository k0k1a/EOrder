package com.cdu.lys.graduation.admin.order.converter;

import com.cdu.lys.graduation.domain.admin.vo.OrderFormInfoVO;
import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;
import com.cdu.lys.graduation.types.payment.DeliveryStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/22 23:38
 */
@Component
public class OrderInfoDataConverter {

    public List<OrderFormInfoVO> convertOrderInfoDO2VO(List<OrderFormInfoDO> orderFormInfoDOS) {
        List<OrderFormInfoVO> res = new ArrayList<>();
        orderFormInfoDOS.forEach(orderFormInfoDO -> {
            OrderFormInfoVO orderFormInfoVO = new OrderFormInfoVO();
            BeanUtils.copyProperties(orderFormInfoDO, orderFormInfoVO);
            orderFormInfoVO.setDeliveryStatus(DeliveryStatus.getMsgByCode(orderFormInfoDO.getDeliveryStatus()));
            res.add(orderFormInfoVO);
        });

        return res;
    }
}

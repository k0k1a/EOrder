package com.cdu.lys.graduation.admin.bill.service.impl;

import com.cdu.lys.graduation.admin.bill.service.BillService;
import com.cdu.lys.graduation.commons.utils.DateUtils;
import com.cdu.lys.graduation.repository.dao.BillDOMapper;
import com.cdu.lys.graduation.repository.dao.GoodsDOMapper;
import com.cdu.lys.graduation.repository.dao.OrderFormDOMapper;
import com.cdu.lys.graduation.repository.dao.OrderFormInfoDOMapper;
import com.cdu.lys.graduation.repository.entity.*;
import com.cdu.lys.graduation.types.payment.TradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyinsong
 * @date 2022/4/6 17:38
 */
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private OrderFormDOMapper orderFormDOMapper;

    @Autowired
    private OrderFormInfoDOMapper orderFormInfoDOMapper;

    @Autowired
    private GoodsDOMapper goodsDOMapper;

    @Autowired
    private BillDOMapper billDOMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void billTask() {
        Date today = DateUtils.getToday();

        OrderFormDOExample example = new OrderFormDOExample();
        example.createCriteria()
                .andCreateTimeGreaterThanOrEqualTo(today);

        //当天所有订单
        List<OrderFormDO> orderFormDOList = orderFormDOMapper.selectByExample(example);

        double totalOrderAmount = 0;
        double totalActualAmount = 0;
        double totalCost = 0;

        List<OrderFormDO> collect = orderFormDOList.stream().
                filter(orderFormDO -> orderFormDO.getTradeStatus().equals(TradeStatus.FINISHED.getCode())
                        || orderFormDO.getTradeStatus().equals(TradeStatus.NOT_DELIVERY.getCode()))
                .collect(Collectors.toList());

        for (OrderFormDO orderFormDO : collect) {

            totalOrderAmount += orderFormDO.getOrderAmount();
            totalActualAmount += orderFormDO.getPayAmount();
            totalCost += this.getCost(orderFormDO);
        }

        BillDOExample billDOExample = new BillDOExample();
        billDOExample.createCriteria()
                .andBillDateEqualTo(today);

        List<BillDO> billDOS = billDOMapper.selectByExample(billDOExample);

        BillDO billDO = new BillDO();
        billDO.setTotalOrderAmount(totalOrderAmount);
        billDO.setTotalActualAmount(totalActualAmount);
        billDO.setTotalCost(totalCost);
        billDO.setNetIncome(totalActualAmount - totalCost);
        billDO.setTotalNumber(orderFormDOList.size());
        billDO.setCancelNumber(orderFormDOList.size() - collect.size());
        billDO.setBillDate(today);

        if (CollectionUtils.isEmpty(billDOS)) {
            billDOMapper.insertSelective(billDO);
        }else {
            billDO.setId(billDOS.get(0).getId());
            billDOMapper.updateByPrimaryKeySelective(billDO);
        }

    }

    /**
     * 计算成本
     * @param orderFormDO 订单
     * @return 成本
     */
    private double getCost(OrderFormDO orderFormDO) {
        OrderFormInfoDOExample example = new OrderFormInfoDOExample();
        example.createCriteria()
                .andOrderNumEqualTo(orderFormDO.getOrderNum());

        double cost = 0;
        List<OrderFormInfoDO> orderFormInfoDOS = orderFormInfoDOMapper.selectByExample(example);
        for (OrderFormInfoDO orderFormInfoDO : orderFormInfoDOS) {
            Integer goodsId = orderFormInfoDO.getGoodsId();
            GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(goodsId);
            cost += goodsDO.getCost();
        }

        return cost;
    }

}

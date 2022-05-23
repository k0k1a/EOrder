package com.cdu.lys.graduation.admin.bill.bo.impl;

import com.cdu.lys.graduation.admin.bill.bo.BillBO;
import com.cdu.lys.graduation.repository.dao.BillDOMapper;
import com.cdu.lys.graduation.repository.entity.BillDO;
import com.cdu.lys.graduation.repository.entity.BillDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liyinsong
 * @date 2022/4/6 16:37
 */
@Service
public class BillBOImpl implements BillBO {

    @Autowired
    private BillDOMapper billDOMapper;

    @Override
    public List<BillDO> getAll() {
        BillDOExample example = new BillDOExample();
        example.setOrderByClause("bill_date desc");
        return billDOMapper.selectByExample(example);
    }

    @Override
    public List<BillDO> getByDate(Date date) {
        BillDOExample example = new BillDOExample();
        example.setOrderByClause("bill_date desc");
        if (Objects.nonNull(date)) {
            example.createCriteria()
                    .andBillDateEqualTo(date);
        }
        return billDOMapper.selectByExample(example);
    }

}

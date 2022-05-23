package com.cdu.lys.graduation.admin.bill.bo;

import com.cdu.lys.graduation.repository.entity.BillDO;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/6 16:36
 */
public interface BillBO {

    List<BillDO> getAll();

    List<BillDO> getByDate(Date date);
}

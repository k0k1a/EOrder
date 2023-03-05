package com.cdu.lys.graduation.admin.bill.converter;

import com.cdu.lys.graduation.domain.admin.vo.BillVO;
import com.cdu.lys.graduation.repository.entity.BillDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/6 16:50
 */
@Component
public class BillConverter {

    public List<BillVO> convert2VOList(List<BillDO> allBill) {

        if (CollectionUtils.isEmpty(allBill)) {
            return null;
        }
        List<BillVO> res = new ArrayList<>();
        allBill.forEach(billDO -> {
            BillVO billVO = new BillVO();
            BeanUtils.copyProperties(billDO, billVO);
            res.add(billVO);
        });

        return res;
    }
}

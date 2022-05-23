package com.cdu.lys.graduation.repository.dao.ext;

import com.cdu.lys.graduation.repository.entity.OrderFormInfoDO;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/9 17:02
 */
public interface OrderFormInfoDOMapperExt {

    List<OrderFormInfoDO> selectByOrderNum(String orderNum);
}

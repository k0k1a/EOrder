package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.OrderFormDO;
import com.cdu.lys.graduation.repository.entity.OrderFormDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderFormDOMapper {
    int countByExample(OrderFormDOExample example);

    int deleteByExample(OrderFormDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderFormDO record);

    int insertSelective(OrderFormDO record);

    List<OrderFormDO> selectByExample(OrderFormDOExample example);

    OrderFormDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderFormDO record, @Param("example") OrderFormDOExample example);

    int updateByExample(@Param("record") OrderFormDO record, @Param("example") OrderFormDOExample example);

    int updateByPrimaryKeySelective(OrderFormDO record);

    int updateByPrimaryKey(OrderFormDO record);
}
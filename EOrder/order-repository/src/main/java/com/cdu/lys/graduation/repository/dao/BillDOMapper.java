package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.BillDO;
import com.cdu.lys.graduation.repository.entity.BillDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillDOMapper {
    int countByExample(BillDOExample example);

    int deleteByExample(BillDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BillDO record);

    int insertSelective(BillDO record);

    List<BillDO> selectByExample(BillDOExample example);

    BillDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BillDO record, @Param("example") BillDOExample example);

    int updateByExample(@Param("record") BillDO record, @Param("example") BillDOExample example);

    int updateByPrimaryKeySelective(BillDO record);

    int updateByPrimaryKey(BillDO record);
}
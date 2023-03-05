package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.SystemRecordDO;
import com.cdu.lys.graduation.repository.entity.SystemRecordDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemRecordDOMapper {
    int countByExample(SystemRecordDOExample example);

    int deleteByExample(SystemRecordDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemRecordDO record);

    int insertSelective(SystemRecordDO record);

    List<SystemRecordDO> selectByExample(SystemRecordDOExample example);

    SystemRecordDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemRecordDO record, @Param("example") SystemRecordDOExample example);

    int updateByExample(@Param("record") SystemRecordDO record, @Param("example") SystemRecordDOExample example);

    int updateByPrimaryKeySelective(SystemRecordDO record);

    int updateByPrimaryKey(SystemRecordDO record);
}
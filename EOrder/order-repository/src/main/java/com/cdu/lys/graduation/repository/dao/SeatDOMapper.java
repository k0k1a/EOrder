package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.SeatDO;

public interface SeatDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SeatDO record);

    int insertSelective(SeatDO record);

    SeatDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SeatDO record);

    int updateByPrimaryKey(SeatDO record);
}
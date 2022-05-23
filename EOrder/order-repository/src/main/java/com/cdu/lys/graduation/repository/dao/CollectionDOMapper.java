package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.CollectionDO;

public interface CollectionDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollectionDO record);

    int insertSelective(CollectionDO record);

    CollectionDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectionDO record);

    int updateByPrimaryKey(CollectionDO record);
}
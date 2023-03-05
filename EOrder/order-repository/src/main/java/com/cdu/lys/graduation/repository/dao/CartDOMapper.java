package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.CartDO;

public interface CartDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CartDO record);

    int insertSelective(CartDO record);

    CartDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CartDO record);

    int updateByPrimaryKey(CartDO record);
}
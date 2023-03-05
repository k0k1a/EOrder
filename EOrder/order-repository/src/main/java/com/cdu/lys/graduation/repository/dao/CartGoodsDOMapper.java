package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.CartGoodsDO;

public interface CartGoodsDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CartGoodsDO record);

    int insertSelective(CartGoodsDO record);

    CartGoodsDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CartGoodsDO record);

    int updateByPrimaryKey(CartGoodsDO record);
}
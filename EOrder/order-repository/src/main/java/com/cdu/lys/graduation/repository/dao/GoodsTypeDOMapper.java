package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.GoodsTypeDOExample;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsTypeDOMapper {
    int countByExample(GoodsTypeDOExample example);

    int deleteByExample(GoodsTypeDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsTypeDO record);

    int insertSelective(GoodsTypeDO record);

    List<GoodsTypeDO> selectByExample(GoodsTypeDOExample example);

    GoodsTypeDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsTypeDO record, @Param("example") GoodsTypeDOExample example);

    int updateByExample(@Param("record") GoodsTypeDO record, @Param("example") GoodsTypeDOExample example);

    int updateByPrimaryKeySelective(GoodsTypeDO record);

    int updateByPrimaryKey(GoodsTypeDO record);
}
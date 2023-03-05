package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.GoodsPictureDO;
import com.cdu.lys.graduation.repository.entity.GoodsPictureDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsPictureDOMapper {
    int countByExample(GoodsPictureDOExample example);

    int deleteByExample(GoodsPictureDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsPictureDO record);

    int insertSelective(GoodsPictureDO record);

    List<GoodsPictureDO> selectByExample(GoodsPictureDOExample example);

    GoodsPictureDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsPictureDO record, @Param("example") GoodsPictureDOExample example);

    int updateByExample(@Param("record") GoodsPictureDO record, @Param("example") GoodsPictureDOExample example);

    int updateByPrimaryKeySelective(GoodsPictureDO record);

    int updateByPrimaryKey(GoodsPictureDO record);
}
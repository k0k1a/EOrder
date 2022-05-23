package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.PictureDO;
import com.cdu.lys.graduation.repository.entity.PictureDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureDOMapper {
    int countByExample(PictureDOExample example);

    int deleteByExample(PictureDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PictureDO record);

    int insertSelective(PictureDO record);

    List<PictureDO> selectByExampleWithBLOBs(PictureDOExample example);

    List<PictureDO> selectByExample(PictureDOExample example);

    PictureDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PictureDO record, @Param("example") PictureDOExample example);

    int updateByExampleWithBLOBs(@Param("record") PictureDO record, @Param("example") PictureDOExample example);

    int updateByExample(@Param("record") PictureDO record, @Param("example") PictureDOExample example);

    int updateByPrimaryKeySelective(PictureDO record);

    int updateByPrimaryKeyWithBLOBs(PictureDO record);

    int updateByPrimaryKey(PictureDO record);
}
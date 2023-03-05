package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.MerchantRealPictureDO;
import com.cdu.lys.graduation.repository.entity.MerchantRealPictureDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantRealPictureDOMapper {
    int countByExample(MerchantRealPictureDOExample example);

    int deleteByExample(MerchantRealPictureDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchantRealPictureDO record);

    int insertSelective(MerchantRealPictureDO record);

    List<MerchantRealPictureDO> selectByExample(MerchantRealPictureDOExample example);

    MerchantRealPictureDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantRealPictureDO record, @Param("example") MerchantRealPictureDOExample example);

    int updateByExample(@Param("record") MerchantRealPictureDO record, @Param("example") MerchantRealPictureDOExample example);

    int updateByPrimaryKeySelective(MerchantRealPictureDO record);

    int updateByPrimaryKey(MerchantRealPictureDO record);
}
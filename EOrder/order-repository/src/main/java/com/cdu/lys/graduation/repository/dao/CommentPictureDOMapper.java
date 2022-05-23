package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.CommentPictureDO;
import com.cdu.lys.graduation.repository.entity.CommentPictureDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentPictureDOMapper {
    int countByExample(CommentPictureDOExample example);

    int deleteByExample(CommentPictureDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentPictureDO record);

    int insertSelective(CommentPictureDO record);

    List<CommentPictureDO> selectByExample(CommentPictureDOExample example);

    CommentPictureDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentPictureDO record, @Param("example") CommentPictureDOExample example);

    int updateByExample(@Param("record") CommentPictureDO record, @Param("example") CommentPictureDOExample example);

    int updateByPrimaryKeySelective(CommentPictureDO record);

    int updateByPrimaryKey(CommentPictureDO record);
}
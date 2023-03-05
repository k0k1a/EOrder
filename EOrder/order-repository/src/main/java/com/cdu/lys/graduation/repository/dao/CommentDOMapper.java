package com.cdu.lys.graduation.repository.dao;

import com.cdu.lys.graduation.repository.entity.CommentDO;
import com.cdu.lys.graduation.repository.entity.CommentDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDOMapper {
    int countByExample(CommentDOExample example);

    int deleteByExample(CommentDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentDO record);

    int insertSelective(CommentDO record);

    List<CommentDO> selectByExample(CommentDOExample example);

    CommentDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentDO record, @Param("example") CommentDOExample example);

    int updateByExample(@Param("record") CommentDO record, @Param("example") CommentDOExample example);

    int updateByPrimaryKeySelective(CommentDO record);

    int updateByPrimaryKey(CommentDO record);
}
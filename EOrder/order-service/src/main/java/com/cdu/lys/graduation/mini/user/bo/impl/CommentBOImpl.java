package com.cdu.lys.graduation.mini.user.bo.impl;

import com.cdu.lys.graduation.domain.comment.dto.CommentDTO;
import com.cdu.lys.graduation.domain.comment.dto.CommentPictureDTO;
import com.cdu.lys.graduation.mini.user.bo.CommentBO;
import com.cdu.lys.graduation.repository.dao.CommentDOMapper;
import com.cdu.lys.graduation.repository.dao.CommentPictureDOMapper;
import com.cdu.lys.graduation.repository.dao.UserDOMapper;
import com.cdu.lys.graduation.repository.entity.*;
import com.cdu.lys.graduation.types.user.CommentQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/29 23:10
 */
@Service
public class CommentBOImpl implements CommentBO {

    @Autowired
    private CommentDOMapper commentDOMapper;

    @Autowired
    private CommentPictureDOMapper commentPictureDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public int saveComment(CommentQuery query, Integer userId) {
        Date createTime = new Date();
        CommentDO commentDO = new CommentDO();

        BeanUtils.copyProperties(query, commentDO);
        commentDO.setUserId(userId);
        commentDO.setEnvironmentScore(query.getEnvScore());
        commentDO.setCreateTime(createTime);
        commentDO.setUpdateTime(createTime);
        commentDOMapper.insertSelective(commentDO);

        return commentDO.getId();
    }

    @Override
    public boolean savePicture(List<String> pictures, Integer commentId) {
        if (CollectionUtils.isEmpty(pictures)) {
            return false;
        }
        Date createTime = new Date();
        //插入图片表
        pictures.forEach(pic->{
            CommentPictureDO commentPictureDO = new CommentPictureDO();
            commentPictureDO.setCommentId(commentId);
            commentPictureDO.setPicUrl(pic);
            commentPictureDO.setCreateTime(createTime);
            commentPictureDO.setUpdateTime(createTime);

            commentPictureDOMapper.insertSelective(commentPictureDO);
        });

        return true;
    }

    @Override
    public List<CommentDTO> getAllComment() {
        CommentDOExample example = new CommentDOExample();
        example.createCriteria()
                .andIsDeleteEqualTo("n");

        List<CommentDO> commentDOS = commentDOMapper.selectByExample(example);

        List<CommentDTO> commentDTOList = this.convert2CommentDTO(commentDOS);

        //设置图片
        this.setOtherCommentData(commentDTOList);

        return commentDTOList;
    }

    @Override
    public List<CommentDTO> getUserComment(Integer userId) {
        CommentDOExample commentDOExample = new CommentDOExample();
        commentDOExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andIsDeleteEqualTo("n");

        List<CommentDO> commentDOS = commentDOMapper.selectByExample(commentDOExample);

        List<CommentDTO> commentDTOList = this.convert2CommentDTO(commentDOS);
        this.setOtherCommentData(commentDTOList);

        return commentDTOList;
    }

    @Override
    public List<CommentDTO> getCommentByType(Integer type) {
        CommentDOExample example = new CommentDOExample();
        CommentDOExample.Criteria criteria = example.createCriteria();
        if (type == 1) {
            example.setOrderByClause("create_time desc");
        } else if (type == 2) {
            criteria
                    .andServiceScoreGreaterThanOrEqualTo(3)
                    .andTasteScoreGreaterThanOrEqualTo(3)
                    .andEnvironmentScoreGreaterThanOrEqualTo(3);
        } else if (type == 3) {
            criteria.andTasteScoreLessThan(3)
                    .andEnvironmentScoreLessThan(3)
                    .andServiceScoreLessThan(3);
        }

        criteria.andIsDeleteEqualTo("n");
        List<CommentDO> commentDOS = commentDOMapper.selectByExample(example);

        List<CommentDTO> commentDTOList = this.convert2CommentDTO(commentDOS);
        this.setOtherCommentData(commentDTOList);
        return commentDTOList;
    }

    /**
     * 设置评论用户、图片信息
     * @param commentDTOList 评论列表
     */
    private void setOtherCommentData(List<CommentDTO> commentDTOList) {
        if (CollectionUtils.isEmpty(commentDTOList)) {
            return;
        }

        commentDTOList.forEach(commentDTO -> {
            //图片
            List<CommentPictureDO> commentPictureDOS = this.getPicturesByCommentId(commentDTO.getId());
            List<CommentPictureDTO> commentPictureDTOS = this.convert2PictureDTO(commentPictureDOS);

            //用户
            UserDO userDO = userDOMapper.selectByPrimaryKey(commentDTO.getUserId());

            commentDTO.setPicUrls(commentPictureDTOS);
            commentDTO.setUsername(userDO.getUsername());
            commentDTO.setAvatarUrl(userDO.getAvatarUrl());
            commentDTO.setNickname(userDO.getNickname());
        });
    }

    /**
     * 根据commentId得到评论图片
     * @param commentId commentId
     * @return
     */
    private List<CommentPictureDO> getPicturesByCommentId(Integer commentId) {
        CommentPictureDOExample example = new CommentPictureDOExample();
        example.createCriteria()
                .andCommentIdEqualTo(commentId)
                .andIsDeleteEqualTo("n");

        return commentPictureDOMapper.selectByExample(example);
    }

    private List<CommentDTO> convert2CommentDTO(List<CommentDO> commentDOS) {
        if (CollectionUtils.isEmpty(commentDOS)) {
            return null;
        }
        List<CommentDTO> res = new ArrayList<>();
        commentDOS.forEach(commentDO -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(commentDO, commentDTO);
            res.add(commentDTO);
        });

        return res;
    }

    private List<CommentPictureDTO> convert2PictureDTO(List<CommentPictureDO> commentPictureDOS) {
        if (CollectionUtils.isEmpty(commentPictureDOS)) {
            return null;
        }
        List<CommentPictureDTO> res = new ArrayList<>();
        commentPictureDOS.forEach(commentPictureDO -> {
            CommentPictureDTO commentPictureDTO = new CommentPictureDTO();
            BeanUtils.copyProperties(commentPictureDO, commentPictureDTO);
            res.add(commentPictureDTO);
        });
        return res;
    }

}

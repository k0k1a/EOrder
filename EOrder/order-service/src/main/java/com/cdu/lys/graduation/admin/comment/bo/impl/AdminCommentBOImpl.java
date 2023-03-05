package com.cdu.lys.graduation.admin.comment.bo.impl;

import com.cdu.lys.graduation.admin.comment.bo.AdminCommentBO;
import com.cdu.lys.graduation.admin.user.bo.AdminUserBO;
import com.cdu.lys.graduation.domain.admin.dto.CommentSearchDTO;
import com.cdu.lys.graduation.domain.comment.dto.CommentDTO;
import com.cdu.lys.graduation.repository.dao.CommentDOMapper;
import com.cdu.lys.graduation.repository.dao.UserDOMapper;
import com.cdu.lys.graduation.repository.entity.CommentDO;
import com.cdu.lys.graduation.repository.entity.CommentDOExample;
import com.cdu.lys.graduation.repository.entity.UserDO;
import com.cdu.lys.graduation.types.ExpireTypeEnum;
import com.cdu.lys.graduation.types.admin.comment.CommentReplyQuery;
import com.cdu.lys.graduation.types.admin.comment.CommentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liyinsong
 * @date 2022/4/2 11:01
 */
@Service
public class AdminCommentBOImpl implements AdminCommentBO {

    @Autowired
    private CommentDOMapper commentDOMapper;

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private AdminUserBO adminUserBO;

    @Override
    public List<CommentDO> getCommentBeforeDate(Date date) {
        CommentDOExample example = new CommentDOExample();
        example.createCriteria()
                .andCreateTimeGreaterThan(date)
                .andIsDeleteEqualTo("n");

        return commentDOMapper.selectByExample(example);
    }

    @Override
    public List<CommentDTO> search(CommentSearchDTO query) {
        CommentDOExample example = new CommentDOExample();
        CommentDOExample.Criteria criteria = example.createCriteria();

        UserDO userDO = null;
        if (StringUtils.hasLength(query.getUsername())) {
            userDO = adminUserBO.getByUsername(query.getUsername());
            if(Objects.nonNull(userDO))
                criteria.andUserIdEqualTo(userDO.getId());
        }

        //好评、差评
        Integer commentType = query.getCommentType();
        if (Objects.nonNull(commentType) && CommentType.isCommentType(commentType)) {
            if (CommentType.BAD_COMMENT.getType().equals(commentType)) {
                criteria.andTasteScoreLessThan(3)
                        .andEnvironmentScoreLessThan(3)
                        .andServiceScoreLessThan(3);
            }else {
                criteria.andTasteScoreGreaterThanOrEqualTo(3)
                        .andEnvironmentScoreGreaterThanOrEqualTo(3)
                        .andServiceScoreGreaterThanOrEqualTo(3);
            }
        }
        criteria.andIsDeleteEqualTo("n");
        List<CommentDO> commentDOS = commentDOMapper.selectByExample(example);

        List<CommentDTO> result = new ArrayList<>();
        for (CommentDO commentDO : commentDOS) {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(commentDO, commentDTO);
            if (Objects.nonNull(userDO)) {
                BeanUtils.copyProperties(userDO, commentDTO);
            }
            result.add(commentDTO);
        }

        return result;
    }

    @Override
    public boolean reply(CommentReplyQuery query) {
        Date replyTime = new Date();
        CommentDO commentDO = new CommentDO();
        BeanUtils.copyProperties(query, commentDO);
        commentDO.setIsReply(ExpireTypeEnum.YES.getType());
        commentDO.setReplyTime(replyTime);
        commentDO.setUpdateTime(replyTime);

        return commentDOMapper.updateByPrimaryKeySelective(commentDO) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        CommentDOExample example = new CommentDOExample();
        example.createCriteria()
                .andIdEqualTo(id)
                .andIsDeleteEqualTo(ExpireTypeEnum.NO.getType());
        CommentDO commentDO = new CommentDO();
        commentDO.setIsDelete(ExpireTypeEnum.YES.getType());
        commentDO.setUpdateTime(new Date());

        return commentDOMapper.updateByExampleSelective(commentDO, example) > 0;
    }

    @Override
    public int autoReply(String replyContent) {
        CommentDOExample example = new CommentDOExample();
        example.createCriteria()
                .andIsReplyEqualTo(ExpireTypeEnum.NO.getType())
                .andServiceScoreGreaterThanOrEqualTo(3)
                .andTasteScoreGreaterThanOrEqualTo(3)
                .andEnvironmentScoreGreaterThanOrEqualTo(3);

        CommentDO record = new CommentDO();
        record.setReply(replyContent);
        record.setReplyTime(new Date());
        record.setIsReply(ExpireTypeEnum.YES.getType());
        return commentDOMapper.updateByExampleSelective(record, example);
    }

}

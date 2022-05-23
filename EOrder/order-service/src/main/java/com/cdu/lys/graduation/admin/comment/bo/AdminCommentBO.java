package com.cdu.lys.graduation.admin.comment.bo;

import com.cdu.lys.graduation.domain.admin.dto.CommentSearchDTO;
import com.cdu.lys.graduation.domain.comment.dto.CommentDTO;
import com.cdu.lys.graduation.repository.entity.CommentDO;
import com.cdu.lys.graduation.types.admin.comment.CommentReplyQuery;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/2 11:01
 */
public interface AdminCommentBO {

    /**
     * 得到过去date到现在的评论
     *
     * @param date 时间
     * @return 评分
     */
    List<CommentDO> getCommentBeforeDate(Date date);

    /**
     * 根据条件搜索
     *
     * @param query 条件
     * @return 结果
     */
    List<CommentDTO> search(CommentSearchDTO query);

    boolean reply(CommentReplyQuery query);

    boolean delete(Integer id);

    int autoReply(String replyContent);
}

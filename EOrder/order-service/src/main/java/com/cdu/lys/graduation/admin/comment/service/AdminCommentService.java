package com.cdu.lys.graduation.admin.comment.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.comment.dto.CommentDTO;
import com.cdu.lys.graduation.types.admin.comment.CommentSearchQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/2 11:15
 */
public interface AdminCommentService {

    PageResult<List<CommentDTO>> search(CommentSearchQuery query);

    int autoReply();

    int calculateCommentScore();
}

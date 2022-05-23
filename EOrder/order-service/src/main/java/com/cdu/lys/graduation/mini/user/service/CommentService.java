package com.cdu.lys.graduation.mini.user.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.comment.dto.CommentDTO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.user.CommentQuery;
import com.cdu.lys.graduation.types.user.CommentTypeQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/29 19:33
 */
public interface CommentService {

    boolean comment(CommentQuery query, Integer userId);

    PageResult<List<CommentDTO>> commentList(PageQuery query);

    PageResult<List<CommentDTO>> getUserCommentList(PageQuery query, Integer userId);

    PageResult<List<CommentDTO>> commentListByType(CommentTypeQuery query);
}

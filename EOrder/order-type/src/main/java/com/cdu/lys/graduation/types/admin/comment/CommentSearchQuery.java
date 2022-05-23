package com.cdu.lys.graduation.types.admin.comment;

import com.cdu.lys.graduation.types.PageQuery;
import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/4/2 10:57
 */
@Data
public class CommentSearchQuery extends PageQuery {

    private String username;

    private Integer commentType;
}

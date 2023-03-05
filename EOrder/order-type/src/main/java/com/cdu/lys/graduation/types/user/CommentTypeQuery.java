package com.cdu.lys.graduation.types.user;

import com.cdu.lys.graduation.types.PageQuery;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liyinsong
 * @date 2022/4/2 15:51
 */
@Data
public class CommentTypeQuery extends PageQuery {

    @NotNull(message = "不能为空")
    private Integer type;

}

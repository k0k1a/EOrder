package com.cdu.lys.graduation.types.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/29 19:34
 */
@Data
public class CommentQuery {

    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNum;

    /**
     * 评论图片
     */
    private List<String> pics;

    /**
     * 服务
     */
    @NotNull(message = "服务评分不能为空")
    private Integer serviceScore;

    /**
     * 环境
     */
    @NotNull(message = "环境评分不能为空")
    private Integer envScore;

    /**
     * 口味
     */
    @NotNull(message = "口味评分不能为空")
    private Integer tasteScore;

    /**
     * 评价内容
     */
    @Length(max = 100,message = "评论长度不能超过100")
    private String content;

}

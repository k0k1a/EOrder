package com.cdu.lys.graduation.types.goods;

import com.cdu.lys.graduation.types.PageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liyinsong
 * @date 2022/2/14 19:04
 */
@Data
@ApiModel(description = "GoodList请求")
public class GoodsListQuery extends PageQuery {
    @NotNull(message = "类型不能为空")
    private Integer goodsType;
}

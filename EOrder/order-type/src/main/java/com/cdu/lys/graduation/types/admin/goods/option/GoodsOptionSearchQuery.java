package com.cdu.lys.graduation.types.admin.goods.option;

import com.cdu.lys.graduation.types.PageQuery;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liyinsong
 * @date 2022/4/5 22:42
 */
@Data
public class GoodsOptionSearchQuery extends PageQuery {
    @NotBlank(message = "名称不能为空")
    private String goodsName;
}

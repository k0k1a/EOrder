package com.cdu.lys.graduation.types.admin.goods.type;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liyinsong
 * @date 2022/3/24 23:49
 */
@Data
public class GoodsTypeUpdateQuery extends GoodsTypeQuery {

    @NotNull(message = "商品类型id不能为null")
    private Integer id;
}

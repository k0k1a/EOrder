package com.cdu.lys.graduation.types.admin.goods;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liyinsong
 * @date 2022/3/20 15:12
 */
@Data
public class UpdateGoodsQuery extends GoodsQuery {
    @NotNull(message = "id不能为null")
    private Integer id;
}

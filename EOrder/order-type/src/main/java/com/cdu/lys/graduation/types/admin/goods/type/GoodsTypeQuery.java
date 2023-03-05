package com.cdu.lys.graduation.types.admin.goods.type;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author liyinsong
 * @date 2022/3/24 23:49
 */
@Data
public class GoodsTypeQuery {

    @NotBlank(message = "商品类型不能为空")
    private String type;
}

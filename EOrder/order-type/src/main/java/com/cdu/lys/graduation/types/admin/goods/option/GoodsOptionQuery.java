package com.cdu.lys.graduation.types.admin.goods.option;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/26 20:26
 */
@Data
public class GoodsOptionQuery {

    @NotNull(message = "商品id不能为空")
    private Integer goodsId;

    @NotBlank(message = "规格名称不能为空")
    private String OptionName;

    private List<OptionItem> optionItems;
}

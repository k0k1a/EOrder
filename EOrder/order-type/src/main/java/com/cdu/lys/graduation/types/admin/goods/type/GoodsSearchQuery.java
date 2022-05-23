package com.cdu.lys.graduation.types.admin.goods.type;

import com.cdu.lys.graduation.types.PageQuery;
import lombok.Data;

import javax.validation.constraints.DecimalMin;

/**
 * @author liyinsong
 * @date 2022/3/26 17:02
 */
@Data
public class GoodsSearchQuery extends PageQuery {

    private String goodsName;

    private Integer goodsType;

    private String taste;

    @DecimalMin(value = "0", message = "价格必须大于0")
    private Double minPrice;

    @DecimalMin(value = "0", message = "价格必须大于0")
    private Double maxPrice;

    @DecimalMin(value = "0", message = "价格必须大于0")
    private Double minCost;

    @DecimalMin(value = "0", message = "价格必须大于0")
    private Double maxCost;
}

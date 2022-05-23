package com.cdu.lys.graduation.types.admin.goods;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/19 18:51
 */
@Data
public class GoodsQuery {

    @Length(min = 1,max = 30, message = "商品名称过长")
    private String goodsName;

    @Length(max = 100, message = "商品描述过长")
    private String description;

    @Length(max = 50, message = "原料描述过长")
    private String ingredients;

    @Length(max = 20, message = "分量描述过长")
    private String weight;

    @Length(max = 20, message = "口味描述过长")
    private String taste;

    @Length(max = 1)
    private String isMeat;

    @NotNull(message = "商品价格不能为空")
    private Double price;

    @NotNull(message = "商品库存不能为空")
    private Integer stock;

    @NotNull(message = "商品成本不能为空")
    private Double cost;

    @NotNull(message = "商品类型不能为空")
    private Integer goodsType;

    private List<Picture> pictures;
}

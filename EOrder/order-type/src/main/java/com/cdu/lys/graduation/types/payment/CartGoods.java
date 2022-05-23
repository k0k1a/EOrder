package com.cdu.lys.graduation.types.payment;

import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/8 10:47
 */
@Data
public class CartGoods {

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 用户选择的选项的id集合
     */
    private List<Integer> optionIds;

    /**
     * 商品数量
     */
    @Min(value = 1,message = "数量至少为1")
    private int quantity;
}
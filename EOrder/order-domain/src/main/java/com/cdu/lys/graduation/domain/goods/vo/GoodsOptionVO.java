package com.cdu.lys.graduation.domain.goods.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/21 11:16
 */
@Data
public class GoodsOptionVO {

    private Integer id;

    private String optionName;

    private List<GoodsOptionItemVO> goodsOptionItems;

}

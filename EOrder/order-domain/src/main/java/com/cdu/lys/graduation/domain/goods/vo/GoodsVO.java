package com.cdu.lys.graduation.domain.goods.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/21 10:55
 */
@Data
public class GoodsVO {
    private Integer id;

    private String goodsName;

    private String description;

    private String ingredients;

    private String weight;

    private String taste;

    private String isMeat;

    private Double price;

    private Integer sales;

    private Double score;

    private List<GoodsPictureVO> pictures;

    private List<GoodsOptionVO> goodsOptions;
}

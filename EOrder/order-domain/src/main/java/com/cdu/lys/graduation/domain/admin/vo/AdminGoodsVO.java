package com.cdu.lys.graduation.domain.admin.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/3/26 16:40
 */
@Data
public class AdminGoodsVO {
    private Integer id;

    private String goodsName;

    private String description;

    private String ingredients;

    private String weight;

    private String taste;

    private String isMeat;

    private Double price;

    private Integer stock;

    private Integer sales;

    private Integer score;

    private Double cost;

    private String goodsType;

    private Date createTime;

    private Date updateTime;

}

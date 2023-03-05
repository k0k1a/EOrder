package com.cdu.lys.graduation.domain.goods.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/2/16 10:33
 */
@Data
public class GoodsOptionItemDTO {

    private Integer id;

    private String optionItem;

    private Double extraPrice;

    private Date createTime;

    private Date updateTime;
}

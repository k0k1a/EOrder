package com.cdu.lys.graduation.domain.goods.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/16 10:30
 */
@Data
public class GoodsOptionDTO {

    private Integer id;

    private String optionName;

    private Integer goodsId;

    private Date createTime;

    private Date updateTime;

    private List<GoodsOptionItemDTO> goodsOptionItems;

}

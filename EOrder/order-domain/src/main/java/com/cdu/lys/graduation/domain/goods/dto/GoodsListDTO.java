package com.cdu.lys.graduation.domain.goods.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/8 10:17
 */
@Data
public class GoodsListDTO {

    private String type;

    private Date createTime;

    private Date updateTime;

    private List<GoodsDTO> goods;
}

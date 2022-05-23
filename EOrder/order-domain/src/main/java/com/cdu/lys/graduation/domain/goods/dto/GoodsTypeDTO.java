package com.cdu.lys.graduation.domain.goods.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/2/13 10:24
 */
@Data
public class GoodsTypeDTO {

    private Integer id;

    private String type;

    private Date createTime;

    private Date updateTime;
}

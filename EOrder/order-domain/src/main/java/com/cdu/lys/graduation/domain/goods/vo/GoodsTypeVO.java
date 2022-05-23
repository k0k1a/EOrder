package com.cdu.lys.graduation.domain.goods.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/2/15 09:42
 */
@Data
public class GoodsTypeVO {
    private Integer id;

    private String type;

    private Date createTime;

    private Date updateTime;
}

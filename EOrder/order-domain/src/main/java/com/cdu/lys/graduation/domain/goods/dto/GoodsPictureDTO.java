package com.cdu.lys.graduation.domain.goods.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/4/1 13:40
 */
@Data
public class GoodsPictureDTO {

    private Integer id;

    private Integer goodsId;

    private String picUrl;

    private String picName;

    private Date createTime;

    private Date updateTime;
}

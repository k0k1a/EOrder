package com.cdu.lys.graduation.domain.picture.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/2/13 17:07
 */
@Data
public class PictureDTO {

    private Integer id;

    private String picName;

    private String picUrl;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    private String base64Data;
}

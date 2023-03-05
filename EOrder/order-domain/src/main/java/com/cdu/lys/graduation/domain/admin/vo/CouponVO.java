package com.cdu.lys.graduation.domain.admin.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/3/21 20:57
 */
@Data
public class CouponVO {
    private Integer id;

    private String couponName;

    private Double reach;

    private Double reduce;

    /**
     * 用户群体
     */
    private String userType;

    private Integer effectiveTime;

    private Date deadline;

    private Integer number;

    private Date createTime;

    private Date updateTime;
}

package com.cdu.lys.graduation.domain.coupon.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/3/2 10:03
 */
@Data
public class UserCouponDTO {
    private Integer id;

    private Integer userId;

    private String couponName;

    private Double reach;

    private Double reduce;

    private Date expireTime;

}

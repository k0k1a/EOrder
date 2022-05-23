package com.cdu.lys.graduation.domain.coupon.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/3/2 10:29
 */
@Data
public class UserCouponVO {
    private Integer id;

    private String couponName;

    private Double reach;

    private Double reduce;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date expireTime;
}

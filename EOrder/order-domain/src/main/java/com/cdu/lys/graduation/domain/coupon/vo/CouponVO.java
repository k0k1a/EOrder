package com.cdu.lys.graduation.domain.coupon.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/2/28 15:52
 */
@Data
public class CouponVO {

    private Integer id;

    private Double reach;

    private Double reduce;

    private String couponName;

    private Integer effectiveTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date deadline;

}

package com.cdu.lys.graduation.domain.user.vo;

import lombok.Data;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/18 21:38
 */
@Data
public class MerchantVO {

    private String storeName;

    private String headerUrl;

    private String description;

    private String businessTime;

    private String province;

    private String city;

    private String district;

    private String address;

    private String phone;

    private String announcement;

    private Double score;

    private List<String> realPictureUrl;
}

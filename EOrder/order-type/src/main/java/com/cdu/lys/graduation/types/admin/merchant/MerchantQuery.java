package com.cdu.lys.graduation.types.admin.merchant;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/15 15:32
 */
@Data
public class MerchantQuery {

    @NotBlank(message = "店铺名称不能为空")
    private String storeName;

    @NotBlank(message = "商户头像不能为空")
    private String headerUrl;

    private String description;

    private String businessTime;

    private String province;

    private String city;

    private String district;

    @NotBlank(message = "详细地址不能为空")
    private String address;

    @NotBlank(message = "电话不能为空")
    @Length(max = 11, message = "电话号码长度不能超过11位")
    private String phone;

    private String announcement;

    /**
     * 商户实景图片
     */
    private List<String> realPictures;

    @NotNull(message = "是否自动回复不能为空")
    private String isAutoReply;

    @Length(max = 100,message = "自动回复长度不能超过100")
    private String autoReplyContent;
}

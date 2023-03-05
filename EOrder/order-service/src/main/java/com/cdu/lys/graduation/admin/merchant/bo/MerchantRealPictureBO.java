package com.cdu.lys.graduation.admin.merchant.bo;

import com.cdu.lys.graduation.repository.entity.MerchantRealPictureDO;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/17 20:56
 */
public interface MerchantRealPictureBO {

    /**
     * 得到商户实景图片
     * @return 图片
     */
    List<MerchantRealPictureDO> get();

    /**
     * 更新商家实体图片
     * @param merchantRealPictureDO 图片
     * @return i
     */
    int update(List<MerchantRealPictureDO> merchantRealPictureDO);
}

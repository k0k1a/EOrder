package com.cdu.lys.graduation.admin.merchant.bo;

import com.cdu.lys.graduation.repository.entity.MerchantDO;

/**
 * @author liyinsong
 * @date 2022/4/14 21:37
 */
public interface MerchantBO {

    /**
     * 得到商户信息
     *
     * @return 商户信息
     */
    MerchantDO get();

    /**
     * 修改商户信息
     *
     * @param merchantDO 商户
     * @return 行数
     */
    int update(MerchantDO merchantDO);
}

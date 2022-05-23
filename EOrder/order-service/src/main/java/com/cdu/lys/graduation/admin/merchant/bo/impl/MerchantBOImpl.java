package com.cdu.lys.graduation.admin.merchant.bo.impl;

import com.cdu.lys.graduation.admin.merchant.bo.MerchantBO;
import com.cdu.lys.graduation.repository.dao.MerchantDOMapper;
import com.cdu.lys.graduation.repository.entity.MerchantDO;
import com.cdu.lys.graduation.repository.entity.MerchantDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/4/14 21:38
 */
@Service
public class MerchantBOImpl implements MerchantBO {

    @Autowired
    private MerchantDOMapper merchantDOMapper;

    @Override
    public MerchantDO get() {
        MerchantDOExample example = new MerchantDOExample();
        List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(merchantDOS)) {
            return null;
        }
        return merchantDOS.get(0);
    }

    @Override
    public int update(MerchantDO merchantDO) {
        MerchantDO old = this.get();
        if (old == null) {
            return merchantDOMapper.insertSelective(merchantDO);
        }
        merchantDO.setId(old.getId());
        return merchantDOMapper.updateByPrimaryKeySelective(merchantDO);
    }
}

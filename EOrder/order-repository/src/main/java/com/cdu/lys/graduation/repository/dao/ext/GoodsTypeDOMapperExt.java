package com.cdu.lys.graduation.repository.dao.ext;

import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/3 10:53
 */
public interface GoodsTypeDOMapperExt {
    /**
     * 查询所有Goods Type
     * @return GoodsTypeDO
     */
    List<GoodsTypeDO> selectAllGoodsType();

    /**
     * 根据value进行模糊查询type
     * @param value 搜索值
     * @return 商品类型
     */
    List<GoodsTypeDO> selectTypeLikeValue(String value);

}

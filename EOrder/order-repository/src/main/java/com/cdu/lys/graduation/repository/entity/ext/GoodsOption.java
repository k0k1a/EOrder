package com.cdu.lys.graduation.repository.entity.ext;

import com.cdu.lys.graduation.repository.entity.GoodsOptionDO;
import com.cdu.lys.graduation.repository.entity.GoodsOptionItemDO;

import java.util.List;

/**
 * Goods中的option类
 *
 * @author liyinsong
 * @date 2022/2/15 18:32
 */
public class GoodsOption extends GoodsOptionDO {

    private List<GoodsOptionItemDO> goodsOptionItems;

    public List<GoodsOptionItemDO> getGoodsOptionItems() {
        return goodsOptionItems;
    }

    public void setGoodsOptionItems(List<GoodsOptionItemDO> goodsOptionItems) {
        this.goodsOptionItems = goodsOptionItems;
    }
}

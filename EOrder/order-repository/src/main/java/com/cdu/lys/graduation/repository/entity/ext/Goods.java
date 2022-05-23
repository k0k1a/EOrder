package com.cdu.lys.graduation.repository.entity.ext;

import com.cdu.lys.graduation.repository.entity.GoodsDO;
import com.cdu.lys.graduation.repository.entity.GoodsPictureDO;

import java.util.List;

/**
 * Goods类
 *
 * @author liyinsong
 * @date 2022/2/13 14:36
 */
public class Goods extends GoodsDO {

    private List<GoodsPictureDO> pictures;

    private List<GoodsOption> goodsOptions;

    public List<GoodsPictureDO> getPictures() {
        return pictures;
    }

    public void setPictures(List<GoodsPictureDO> pictures) {
        this.pictures = pictures;
    }

    public List<GoodsOption> getGoodsOptions() {
        return goodsOptions;
    }

    public void setGoodsOptions(List<GoodsOption> goodsOptions) {
        this.goodsOptions = goodsOptions;
    }
}

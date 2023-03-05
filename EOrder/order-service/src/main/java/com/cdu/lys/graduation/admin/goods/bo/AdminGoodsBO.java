package com.cdu.lys.graduation.admin.goods.bo;

import com.cdu.lys.graduation.repository.entity.GoodsDO;
import com.cdu.lys.graduation.repository.entity.GoodsPictureDO;
import com.cdu.lys.graduation.types.admin.goods.GoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.Picture;
import com.cdu.lys.graduation.types.admin.goods.UpdateGoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsSearchQuery;

import java.util.List;
import java.util.Map;

/**
 * @author liyinsong
 * @date 2022/3/19 11:36
 */
public interface AdminGoodsBO {

    /**
     * 查询所有商品
     * @return 商品列表
     */
    List<GoodsDO> selectGoods();

    /**
     * 查询商品图片
     * @param goodsId 商品id
     * @return 图片
     */
    List<GoodsPictureDO> getGoodsPicturesById(Integer goodsId);

    /**
     * 删除商品（修改is_delete）
     * @param goodsId 商品id
     * @return rows
     */
    int deleteGoodsByGoodsId(Integer goodsId);

    /**
     * 添加商品
     * @param goods 商品
     * @return i
     */
    int addGoods(GoodsQuery goods);

    /**
     * 添加商品图片图片
     * @param pictures 图片
     * @param goodsId 商品id
     * @return 0
     */
    int addGoodsPictures(List<Picture> pictures, Integer goodsId);

    /**
     * 删除商品所有的图片
     * @param goodsId 商品id
     * @return i
     */
    int deleteGoodsPictures(Integer goodsId);

    /**
     * 修改商品
     * @param goods
     * @return
     */
    int updateGoods(UpdateGoodsQuery goods);

    /**
     * 搜索
     * @param query 条件
     * @return 商品集合
     */
    List<GoodsDO> search(GoodsSearchQuery query);

    /**
     * 根据goodsId更新评分
     * @param scores k：goodsId，v：score
     * @return i
     */
    int updateGoodsScore(Map<Integer,Double> scores);
}

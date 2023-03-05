package com.cdu.lys.graduation.repository.dao.ext;

import com.cdu.lys.graduation.repository.dao.ext.query.GoodsListParam;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import com.cdu.lys.graduation.repository.entity.ext.Goods;
import com.cdu.lys.graduation.repository.entity.ext.GoodsList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Goods数据库相关的扩展
 *
 * @author liyinsong
 * @date 2022/2/6 16:57
 */
public interface GoodsMapperExt {

    /**
     * 查询GoodsList
     *
     * @param query 请求参数，支持goodsType
     * @return GoodsList
     */
    List<GoodsList> selectGoodsList(GoodsListParam query);

    /**
     * 查询所有Goods Type
     *
     * @return GoodsTypeDO
     */
    @Deprecated
    List<GoodsTypeDO> selectAllGoodsType();

    /**
     * 根据goods id查询 Goods
     *
     * @param id goods id
     * @return Goods
     */
    Goods selectGoodsById(Integer id);

    /**
     * 查询所有商品
     * @return 商品集合
     */
    List<Goods> selectHotGoods();

    /**
     * 根据GoodsType查询Goods
     *
     * @param goodsType 类型id
     * @return 某个分类的Goods
     */
    List<Goods> selectByGoodsType(Integer goodsType);

    /**
     * 根据goodsName查询Goods
     *
     * @param goodsName 商品名称
     * @return 商品
     */
    List<Goods> selectAllLikeGoodsName(String goodsName);

    /**
     * 根据原料模糊查询
     *
     * @param ingredients 原料
     * @return 商品
     */
    List<Goods> selectAllLikeIngredients(String ingredients);

    /**
     * 根据口味模糊查询
     *
     * @param taste 口味
     * @return 商品
     */
    List<Goods> selectAllLikeTaste(String taste);

    /**
     * 减少商品库存
     *
     * @param id     商品id
     * @param number 减少数量
     * @return
     */
    int decreaseGoodsStockById(@Param("id") Integer id, @Param("number") int number);

    /**
     * 增加商品库存
     *
     * @param id id
     * @param number 数量
     * @return
     */
    int increaseGoodsStockById(@Param("id") Integer id, @Param("number") int number);

    /**
     * 按照id批量更新商品评分
     *
     * @param goodsScoreMap k：goodsId，v：score
     * @return i
     */
    int updateScoreByGoodsIds(@Param("goodsScoreMap") Map<Integer, Double> goodsScoreMap);
}
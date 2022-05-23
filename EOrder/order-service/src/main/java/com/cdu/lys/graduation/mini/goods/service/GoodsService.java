package com.cdu.lys.graduation.mini.goods.service;

import com.cdu.lys.graduation.domain.goods.dto.GoodsDTO;
import com.cdu.lys.graduation.domain.goods.dto.GoodsListDTO;
import com.cdu.lys.graduation.domain.goods.dto.GoodsTypeDTO;
import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.types.PageQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/2/8 10:14
 */
public interface GoodsService {

    /**
     * 分页查询商品list
     *
     * @param pageNo   页数
     * @param pageSize 页面大小
     * @return list
     */
    PageResult<List<GoodsListDTO>> selectGoodsList(Integer pageNo, Integer pageSize);

    /**
     * 查询所有商品类型
     *
     * @return type list
     */
    List<GoodsTypeDTO> selectGoodsTypes();

    /**
     * 根据商品类型查询列表
     *
     * @param pageNo    页数
     * @param pageSize  页面大小
     * @param goodsType goodsType
     * @return 商品list
     */
    PageResult<List<GoodsListDTO>> selectGoodsListByType(Integer pageNo, Integer pageSize, Integer goodsType);

    /**
     * 查询热门商品
     *
     * @param query 分页参数
     * @return 商品list
     */
    PageResult<List<GoodsListDTO>> selectHotGoodsList(PageQuery query);

    /**
     * 根据goods id查询Goods
     * @param id goods id
     * @return GoodsDTO
     */
    GoodsDTO selectGoodsById(Integer id);


    /**
     * 搜索商品
     * @param value 关键词
     * @return 商品
     */
    List<GoodsDTO> searchGoods(String value);

    /**
     * 根据商品id购买商品
     *
     * @param id 商品id
     * @param number 购买的数量
     * @return 是否成功
     */
    boolean buyGoodsById(Integer id, int number);

}

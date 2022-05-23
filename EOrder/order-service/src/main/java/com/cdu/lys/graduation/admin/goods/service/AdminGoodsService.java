package com.cdu.lys.graduation.admin.goods.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.repository.entity.GoodsDO;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.goods.GoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.UpdateGoodsQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsSearchQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/19 11:32
 */
public interface AdminGoodsService {

    /**
     * 商品列表分页查询
     *
     * @param query 分页请求
     * @return 分页数据
     */
    PageResult<List<GoodsDO>> getGoodsList(PageQuery query);

    /**
     * 删除商品
     * @param goodsId 商品id
     * @return 结果
     */
    boolean deleteGoods(Integer goodsId);

    /**
     * 添加商品
     * @param goods 商品
     * @return id
     */
    int addGoods(GoodsQuery goods);

    /**
     * 修改商品
     * @param goods 商品
     * @return i
     */
    int updateGoods(UpdateGoodsQuery goods);

    /**
     * 搜索
     * @param query 条件
     * @return 商品
     */
    PageResult<List<GoodsDO>> search(GoodsSearchQuery query);

    /**
     * 计算商品评分
     * @return i
     */
    int calculateGoodsScore();
}

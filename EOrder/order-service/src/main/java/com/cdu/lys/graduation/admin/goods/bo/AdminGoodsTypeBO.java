package com.cdu.lys.graduation.admin.goods.bo;

import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsTypeQuery;
import com.cdu.lys.graduation.types.admin.goods.type.GoodsTypeUpdateQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/24 23:11
 */
public interface AdminGoodsTypeBO {

    List<GoodsTypeDO> getAll();

    int updateById(GoodsTypeUpdateQuery query);

    int add(GoodsTypeQuery query);

    int delete(Integer id);

    List<GoodsTypeDO> searchByType(String type);
}

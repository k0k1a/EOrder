package com.cdu.lys.graduation.admin.goods.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.repository.entity.GoodsTypeDO;
import com.cdu.lys.graduation.types.PageQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/24 23:14
 */
public interface AdminGoodsTypeService {

    PageResult<List<GoodsTypeDO>> getGoodsTypeList(PageQuery query);
}

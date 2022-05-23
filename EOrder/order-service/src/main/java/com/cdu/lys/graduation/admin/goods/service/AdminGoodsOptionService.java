package com.cdu.lys.graduation.admin.goods.service;

import com.cdu.lys.graduation.commons.result.PageResult;
import com.cdu.lys.graduation.domain.admin.vo.AdminGoodsOption;
import com.cdu.lys.graduation.types.PageQuery;
import com.cdu.lys.graduation.types.admin.goods.option.GoodsOptionSearchQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/26 19:20
 */
public interface AdminGoodsOptionService {

    PageResult<List<AdminGoodsOption>> getAllOptions(PageQuery query);

    PageResult<List<AdminGoodsOption>> search(GoodsOptionSearchQuery query);
}

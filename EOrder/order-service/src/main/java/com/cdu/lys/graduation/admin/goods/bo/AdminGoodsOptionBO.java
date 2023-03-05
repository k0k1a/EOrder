package com.cdu.lys.graduation.admin.goods.bo;

import com.cdu.lys.graduation.domain.admin.vo.AdminGoodsOption;
import com.cdu.lys.graduation.types.admin.goods.option.GoodsOptionQuery;

import java.util.List;

/**
 * @author liyinsong
 * @date 2022/3/26 19:26
 */
public interface AdminGoodsOptionBO {

    List<AdminGoodsOption> selectAll();

    List<AdminGoodsOption> search(String goodsName);

    int addGoodsOption(GoodsOptionQuery query);
}

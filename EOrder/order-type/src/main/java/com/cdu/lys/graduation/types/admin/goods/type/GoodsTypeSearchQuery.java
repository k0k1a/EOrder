package com.cdu.lys.graduation.types.admin.goods.type;

import com.cdu.lys.graduation.types.PageQuery;
import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/3/26 15:17
 */
@Data
public class GoodsTypeSearchQuery extends PageQuery {

    private String type;
}

package com.cdu.lys.graduation.types.admin.order;

import com.cdu.lys.graduation.types.PageQuery;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author liyinsong
 * @date 2022/4/6 14:25
 */
@Data
public class OrderSearchQuery extends PageQuery {
    private String username;

    private String orderNum;

    private String queueNum;

    private Integer deliveryStatus;

    private Integer tradeStatus;

    @NotNull(message = "类型不能为空")
    private Boolean isUndone;
}

package com.cdu.lys.graduation.domain.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/4/13 16:08
 */
@Data
@AllArgsConstructor
public class RecordVO {

    private String number;

    /**
     * 比昨天
     */
    private String rate;
}

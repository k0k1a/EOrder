package com.cdu.lys.graduation.domain.admin.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/4/6 16:51
 */
@Data
public class BillVO {
    private Integer id;

    @ExcelProperty(value = "订单总金额")
    private Double totalOrderAmount;

    @ExcelProperty(value = "实际成交额")
    private Double totalActualAmount;

    @ExcelProperty(value = "总成本")
    private Double totalCost;

    @ExcelProperty(value = "净收入")
    private Double netIncome;

    @ExcelProperty(value = "总订单数")
    private Integer totalNumber;

    @ExcelProperty(value = "取消订单数")
    private Integer cancelNumber;

    @ExcelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date billDate;
}

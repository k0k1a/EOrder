package com.cdu.lys.graduation.types.admin.bill;

import com.cdu.lys.graduation.types.PageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author liyinsong
 * @date 2022/4/18 0:12
 */
@Data
public class BillSearchQuery extends PageQuery {

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @Past(message = "日期必须为过去的时间")
    private Date billDate;
}

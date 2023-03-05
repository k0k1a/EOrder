package com.cdu.lys.graduation.domain.admin.vo;

import com.cdu.lys.graduation.domain.user.vo.MerchantVO;
import lombok.Data;

/**
 * @author liyinsong
 * @date 2022/4/14 22:00
 */
@Data
public class AdminMerchantVO extends MerchantVO {

    private String isAutoReply;

    private String autoReplyContent;
}

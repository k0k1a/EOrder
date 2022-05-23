package com.cdu.lys.graduation.admin.record.bo;

import com.cdu.lys.graduation.repository.entity.SystemRecordDO;

/**
 * @author liyinsong
 * @date 2022/4/13 19:53
 */
public interface RecordBO {

    /**
     * 得到昨天记录
     * @return 昨天记录
     */
    SystemRecordDO getYesterdayRecord();
}

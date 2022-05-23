package com.cdu.lys.graduation.admin.record.service;

import com.cdu.lys.graduation.domain.admin.vo.RecordVO;

/**
 * @author liyinsong
 * @date 2022/4/13 15:27
 */
public interface RecordService {

    /**
     * 日访问量
     * @return 日访问量
     */
    RecordVO getDailyVisit();

    /**
     * 日销量
     * @return 日销量
     */
    RecordVO getDailySales();

    /**
     * 日收入
     * @return 日收入
     */
    RecordVO getDailyIncome();

    /**
     * 日评论量
     * @return 日评论量
     */
    RecordVO getDailyCommentNum();

    /**
     * 持久化每日数据
     * @return 影响行数
     */
    int recordTask();
}

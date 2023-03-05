package com.cdu.lys.graduation.task;

/**
 * @author liyinsong
 * @date 2022/3/15 14:36
 */
public interface TaskService {

    /**
     * 定时更新用户失效的优惠券
     */
    void userCouponExpireTask();

    /**
     * 定时设置用户失效的订单
     */
    void orderUnpaidExpireTask();

    /**
     * 定时计算每日账单
     */
    void billTask();

    /**
     * 记录每日系统记录数据
     */
    void recordDailyNumber();

    /**
     * 自动回复
     */
    void autoReply();

    /**
     * 计算店铺评分
     */
    void calculateCommentScore();

    /**
     * 计算商品评分
     */
    void calculateGoodsScore();
}

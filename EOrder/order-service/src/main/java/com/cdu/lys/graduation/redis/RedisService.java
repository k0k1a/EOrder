package com.cdu.lys.graduation.redis;

import com.cdu.lys.graduation.domain.user.wx.LoginUser;

import java.util.Set;

/**
 *
 * @author liyinsong
 * @date 2022/1/16 15:44
 */
public interface RedisService {

    /**
     * 获取微信登录存在redis的token-value
     * @param token
     * @return
     */
    LoginUser getLoginValue(String token);

    /**
     * 校验key的有效性
     * @param token token
     * @return 是否失效,true 有效，false 失效
     */
    boolean checkTokenValid(String token);

    /**
     * 校验admin token有效性
     * @param token token
     * @return
     */
    boolean checkAdminTokenValid(String token);

    /**
     * 获取key 值
     * @param key key
     * @return 值
     */
    String getKey(String key);

    /**
     * 增加searchValue的搜索此时，如果没有则添加
     */
    void increaseHotSearchList(String searchValue);

    /**
     * 获取top list
     * @return top list
     */
    Set<String> getHotSearchTopList(int top);

    /**
     *
     * @return
     */
    int getDailySales();

    /**
     * 增加访问量
     * @return
     */
    int incrVisitNumber();

    /**
     * 增加日收入
     * @param amount 金额
     * @return
     */
    void incrDailyIncome(Double amount);

    /**
     * 今日评论数
     * @return
     */
    int incrDailyComment();
}

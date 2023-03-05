package com.cdu.lys.graduation.redis.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdu.lys.graduation.commons.utils.JWTUtils;
import com.cdu.lys.graduation.domain.admin.TokenValue;
import com.cdu.lys.graduation.domain.user.dto.UserDTO;
import com.cdu.lys.graduation.domain.user.wx.LoginUser;
import com.cdu.lys.graduation.mini.user.service.UserService;
import com.cdu.lys.graduation.redis.RedisService;
import com.cdu.lys.graduation.types.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author liyinsong
 * @date 2022/1/16 15:45
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserService userService;

    @Value("${system.order.secretKey}")
    private String secretKey;

    @Override
    public LoginUser getLoginValue(String token) {
        String key = SystemConstant.LOGIN_TOKEN_KEY + token;
        String value = redisTemplate.opsForValue().get(key);

        LoginUser loginUser = JSONObject.parseObject(value, LoginUser.class);
        if (!Objects.isNull(loginUser.getId())) {
            return loginUser;
        }

        UserDTO userDTO = null;
        if (StringUtils.hasText(loginUser.getOpenid())) {
            userDTO = userService.selectUserByOpenId(loginUser.getOpenid());
        }

        if (StringUtils.hasText(loginUser.getUsername())) {
            userDTO = userService.selectUserByUsername(loginUser.getUsername());
        }

        loginUser.setId(userDTO.getId());
        return loginUser;
    }

    @Override
    public boolean checkTokenValid(String token) { // TODO 这里应该去检查token而不是存Redis
        String key = SystemConstant.LOGIN_TOKEN_KEY + token;

        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey != null && !hasKey) {
            return false;
        }

        Long expire = redisTemplate.getExpire(key);
        return expire != null && expire > 0;
    }


    @Override
    public boolean checkAdminTokenValid(String token) {
        String username = (String) JWTUtils.parseJWT(token, secretKey).get("username");

        // FIXME 后面的几行可能是垃圾，不用存在redis，只用检查jwt就行了
        String key = SystemConstant.ADMIN_LOGIN_TOKEN_KEY + username;

        String value = redisTemplate.opsForValue().get(key);
        TokenValue tokenValue = JSONObject.parseObject(value, TokenValue.class);

        if (tokenValue == null || !token.equals(tokenValue.getToken())) {
            return false;
        }

        return true;
    }

    @Override
    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void increaseHotSearchList(String searchValue) {

        //如果存在则加1，不存在添加
        Double result = redisTemplate.opsForZSet().incrementScore(SystemConstant.HOT_SEARCH_LIST_KEY, searchValue, 1);
    }

    @Override
    public Set<String> getHotSearchTopList(int top) {
        Set<String> keys = redisTemplate.opsForZSet().reverseRange(SystemConstant.HOT_SEARCH_LIST_KEY, 0, top - 1);
        return keys;
    }

    @Override
    public int getDailySales() {
        String key = SystemConstant.DAILY_SALES_KEY;
        Long sales = redisTemplate.opsForValue().increment(key);
        redisTemplate.expireAt(key, this.getNextDayTime());

        return sales.intValue();
    }

    @Override
    public int incrVisitNumber() {
        String key = SystemConstant.DAILY_VISIT_NUM_KEY;
        Long incr = redisTemplate.opsForValue().increment(key);
        redisTemplate.expireAt(key, this.getNextDayTime());

        return incr.intValue();
    }

    @Override
    public void incrDailyIncome(Double amount) {
        String key = SystemConstant.DAILY_INCOME_KEY;

        double v = 0;
        String income = redisTemplate.opsForValue().get(key);
        if (StringUtils.hasLength(income)) {
            v = Double.parseDouble(income);
        }
        v += amount;
        redisTemplate.opsForValue().set(key, String.valueOf(v));
        redisTemplate.expireAt(key, this.getNextDayTime());
    }

    @Override
    public int incrDailyComment() {
        String key = SystemConstant.DAILY_COMMENT_NUM_KEY;
        Long incr = redisTemplate.opsForValue().increment(key);
        redisTemplate.expireAt(key, this.getNextDayTime());

        return incr.intValue();
    }

    /**
     * 获取第二天凌晨
     *
     * @return 第二天凌晨
     */
    private Date getNextDayTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}

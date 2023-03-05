package com.cdu.lys.graduation.commons.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author liyinsong
 * @date 2022/2/28 16:25
 */
public class DateUtils {

    /**
     * 查询指定日期前后指定的天数
     * @param date 指定日期
     * @param day 天数
     * @return 日期对象
     */
    public static Date add(Date date, int day) {

        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);

        return calendar.getTime();
    }

    /**
     * 得到指定日期后的指定时间
     * @param date 指定日期
     * @param type 按type增加，使用Calendar类中的常量
     * @param amount 数量
     * @return 日期
     */
    public static Date add(Date date, int type, int amount) {
        if (Objects.isNull(date)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, amount);

        return calendar.getTime();
    }

    /**
     * 得到今天凌晨时间
     * @return 时间
     */
    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}

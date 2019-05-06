package com.saliai.wechat_3rdparty.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: chenyiwu
 * @Describtion: 日期处理类
 * @Create Time:2018/6/8
 */
public class DateUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(Date date) {
        try {
            return sdf.format(date);
        } catch (Exception e) {
        }

        return null;
    }

    public static String formatDate(Date date, String format) {
        try {
            return dateFormat(format).format(date);
        } catch (Exception e) {
        }

        return null;
    }

    private static SimpleDateFormat dateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 功能描述: 时间戳转换成时间
     *
     * @auther: Martin、chen
     * @date: 2018/6/8 16:45
     * @param: times
     * @return: Date
     */
    public static Date timestampToDate(long times) {
        return new Date(times);
    }

    public static boolean isBefore(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        return cal.before(today);
    }

    /**
     * 功能描述:判断是否为同一天
     *
     * @param date1
     * @param date2
     * @auther: Martin、chen
     * @date: 2018/6/8 16:45
     * @return: boolean
     */
    public static boolean isSameDate(Date date1, Date date2) {

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }
}

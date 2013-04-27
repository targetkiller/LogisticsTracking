package org.scauhci.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日期工具类

 */
public class Dates {

    private Dates() {
        // 私有构造 
    }
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 创建calendar实例
     *
     * @param date
     * @return
     */
    public static java.util.Calendar newCalendar(java.util.Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 创建calendar实例
     *
     * @param time 毫秒数
     * @return
     */
    public static java.util.Calendar newCalendar(long time) {
        return newCalendar(new java.util.Date(time));
    }

    /**
     * 创建calendar实例
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static java.util.Calendar newCalendar(int year, int month, int day) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        if (year < 100) {
            year = 2000 + year;
        }
        cal.set(year, month - 1, day);
        return cal;
    }

    /**
     * **************************获取格式化符串********************************
     */
    /**
     * 按format格式化日期 字母 日期或时间元素 表示 示例 G Era 标志符 Text AD y 年 Year 1996; 96 M 年中的月份
     * Month July; Jul; 07 w 年中的周数 Number 27 W 月份中的周数 Number 2 D 年中的天数 Number
     * 189 d 月份中的天数 Number 10 F 月份中的星期 Number 2 E 星期中的天数 Text Tuesday; Tue a
     * Am/pm 标记 Text PM H 一天中的小时数（0-23） Number 0 k 一天中的小时数（1-24） Number 24 K
     * am/pm 中的小时数（0-11） Number 0 h am/pm 中的小时数（1-12） Number 12 m 小时中的分钟数 Number
     * 30 s 分钟中的秒数 Number 55 S 毫秒数 Number 978 z 时区 General time zone Pacific
     * Standard Time; PST; GMT-08:00 Z 时区 RFC 822 time zone -0800
     *
     * @param date
     * @param formatStr
     * @return
     */
    public static String getDateFormatStr(java.util.Date date,
            String formatStr) {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat(formatStr);
        return sdf.format(date);
    }

    /**
     * 按format格式化日期 字母 日期或时间元素 表示 示例 G Era 标志符 Text AD y 年 Year 1996; 96 M 年中的月份
     * Month July; Jul; 07 w 年中的周数 Number 27 W 月份中的周数 Number 2 D 年中的天数 Number
     * 189 d 月份中的天数 Number 10 F 月份中的星期 Number 2 E 星期中的天数 Text Tuesday; Tue a
     * Am/pm 标记 Text PM H 一天中的小时数（0-23） Number 0 k 一天中的小时数（1-24） Number 24 K
     * am/pm 中的小时数（0-11） Number 0 h am/pm 中的小时数（1-12） Number 12 m 小时中的分钟数 Number
     * 30 s 分钟中的秒数 Number 55 S 毫秒数 Number 978 z 时区 General time zone Pacific
     * Standard Time; PST; GMT-08:00 Z 时区 RFC 822 time zone -0800
     *
     * @param time 毫秒数
     * @param formatStr
     * @return
     */
    public static String getDateFormatStr(Long time, String formatStr) {
        if (time == null) {
            return null;
        }
        return getDateFormatStr(new java.util.Date(time), formatStr);
    }

    /**
     * 返回类似这样的yyyy-MM-dd HH:mm:ss字符串(24小时)
     *
     * @param date
     * @return
     */
    public static String getDateTimeStr(java.util.Date date) {
        return getDateFormatStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回类似这样的yyyy-MM-dd HH:mm:ss字符串(24小时)
     *
     * @param time 毫秒数
     * @return
     */
    public static String getDateTimeStr(long time) {
        return getDateFormatStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 返回类似这样的yyyy-MM-dd字符串
     *
     * @param date
     * @return
     */
    public static String getDateStr(java.util.Date date) {
        return getDateFormatStr(date, "yyyy-MM-dd");
    }

    /**
     * 返回类似这样的yyyy-MM-dd字符串
     *
     * @param time 毫秒数
     * @return
     */
    public static String getDateStr(Long time) {
        return getDateFormatStr(time, "yyyy-MM-dd");
    }

    
    public static java.util.Date getDateByDateStr(String dateStr) {
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return SDF.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    
    public static Long getDateByDateTimeStr(String dateTimeStr) {
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return SDF.parse(dateTimeStr).getTime();
        } catch (ParseException e) {
            return null;
        }
    }

    public static Long getBirthdayDateTimeByDateStr(String dateStr) {
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar cal1 = Dates.newCalendar(SDF.parse(dateStr));
            Calendar cal2 = Dates.newCalendar(new Date());//今年的几月几日
            cal2.set(Calendar.MONTH, cal1.get(Calendar.MONTH));
            cal2.set(Calendar.DAY_OF_MONTH, cal1.get(Calendar.DAY_OF_MONTH));
            return cal2.getTimeInMillis();
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * **************************获取日期*********************************
     */
    /**
     * 按format格式化字符串返回日期 字母 日期或时间元素 表示 示例 G Era 标志符 Text AD y 年 Year 1996; 96 M
     * 年中的月份 Month July; Jul; 07 w 年中的周数 Number 27 W 月份中的周数 Number 2 D 年中的天数
     * Number 189 d 月份中的天数 Number 10 F 月份中的星期 Number 2 E 星期中的天数 Text Tuesday;
     * Tue a Am/pm 标记 Text PM H 一天中的小时数（0-23） Number 0 k 一天中的小时数（1-24） Number 24
     * K am/pm 中的小时数（0-11） Number 0 h am/pm 中的小时数（1-12） Number 12 m 小时中的分钟数
     * Number 30 s 分钟中的秒数 Number 55 S 毫秒数 Number 978 z 时区 General time zone
     * Pacific Standard Time; PST; GMT-08:00 Z 时区 RFC 822 time zone -0800
     *
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static java.util.Date getDateFromStr(String dateStr,
            String formatStr) {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat(formatStr);
        java.util.Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException ex) {
            Logger.getLogger(
                    Dates.class.getName()).log(Level.SEVERE, "格式化" + dateStr + "出错了", ex);
        }
        return date;
    }

    /**
     * **************************获取整数日期*********************************
     */
    /**
     * 获取整数日期
     *
     * @param date
     * @param field Calendar.常量
     * @return
     */
    public static int getNumByCalendarField(java.util.Date date, int field) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    /**
     * 获取整数日期
     *
     * @param time 毫秒数
     * @param field Calendar.常量
     * @return
     */
    public static int getNumByCalendarField(long time, int field) {
        return getNumByCalendarField(new java.util.Date(time), field);
    }

    /**
     * 返回年份
     *
     * @param time 毫秒数
     * @return
     */
    public static int getYear(long time) {
        return getNumByCalendarField(time, java.util.Calendar.YEAR);
    }

    /**
     * 返回年份
     *
     * @param date
     * @return
     */
    public static int getYear(java.util.Date date) {
        return getNumByCalendarField(date, java.util.Calendar.YEAR);
    }

    /**
     * 返回月份
     *
     * @param time 毫秒数
     * @return
     */
    public static int getMonth(long time) {
        return getNumByCalendarField(time, java.util.Calendar.MONTH) + 1;
    }

    /**
     * 返回月份
     *
     * @param date
     * @return
     */
    public static int getMonth(java.util.Date date) {
        return getNumByCalendarField(date, java.util.Calendar.MONTH) + 1;
    }

    /**
     * **************************计算日期*********************************
     */
    /**
     * 计算两个日期之间的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer daysBetweenDates(java.util.Date startDate,
            java.util.Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        Long interval = endDate.getTime() - startDate.getTime();
        interval /= (24 * 60 * 60 * 1000);
        return interval.intValue();
    }

    /**
     * 计算两个整型日期之间的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer daysBetweenDates(Long startDate,
            Long endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        java.util.Calendar c1 = newCalendar(startDate);
        java.util.Calendar c2 = newCalendar(endDate);
        Long lg = (c2.getTimeInMillis() - c1.getTimeInMillis())
                / 1000 / 60 / 60 / 24;
        return lg.intValue();
    }

    /**
     * 是否是今天之后的时间
     *
     * @param time
     * @return
     */
    public static boolean isAfterToday(long time) {
        long begin = Dates.getTodayBegin();
        Date tomorrow = Dates.getDateAfterDays(new Date(begin), 1);
        return time > tomorrow.getTime();
    }

    /**
     * 得到几天前的时间
     *
     * @param date
     * @param days
     * @return
     */
    public static java.util.Date getDateBeforeDays(java.util.Date date,
            int days) {
        java.util.Calendar now = newCalendar(date);
        now.set(java.util.Calendar.DATE,
                now.get(java.util.Calendar.DATE) - days);
        return now.getTime();
    }

    /**
     * 得到几年前的时间
     *
     * @param date
     * @param years
     * @return
     */
    public static java.util.Date getDateBeforeYears(java.util.Date date,
            int years) {
        java.util.Calendar now = newCalendar(date);
        now.set(java.util.Calendar.YEAR,
                now.get(java.util.Calendar.YEAR) - years);
        return now.getTime();
    }

    /**
     * 得到几年前的时间
     *
     * @param date
     * @param years
     * @return
     */
    public static java.util.Date getDefaultBirthdayBeforeYears(java.util.Date date,
            int years) {
        java.util.Calendar now = newCalendar(date);
        now.set(java.util.Calendar.YEAR,
                now.get(java.util.Calendar.YEAR) - years);
        now.set(java.util.Calendar.DATE, 1);
        now.set(java.util.Calendar.MONTH, 0);//1月
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param date
     * @param days
     * @return
     */
    public static java.util.Date getDateAfterDays(java.util.Date date, int days) {
        java.util.Calendar now = newCalendar(date);
        now.set(java.util.Calendar.DATE,
                now.get(java.util.Calendar.DATE) + days);
        return now.getTime();
    }

    /**
     * @param a
     * @param b
     * @param c
     * @return true or false if b<a<c
     */
    public static boolean isABetweenBAndC(java.util.Date a, java.util.Date b,
            java.util.Date c) {
        if (a.after(b) && a.before(c)) {
            return true;
        }
        return false;
    }

    /**
     * 计算两个整型日期之间的年数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long yearsBetweenDates(java.util.Date startDate,
            java.util.Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        java.util.Calendar c1 = newCalendar(startDate);
        java.util.Calendar c2 = newCalendar(endDate);
        Long lg = (c2.getTimeInMillis() - c1.getTimeInMillis())
                / 1000 / 60 / 60 / 24 / 365;
        return lg;
    }

    /**
     * 计算两个整型日期之间的年数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long yearsBetweenDates(Long startDate,
            Long endDate) {
        if (startDate == null || endDate == null || startDate == 0 || endDate == 0) {
            return null;
        }
        java.util.Calendar c1 = newCalendar(startDate);
        java.util.Calendar c2 = newCalendar(endDate);
        Long lg = (c2.getTimeInMillis() - c1.getTimeInMillis())
                / 1000 / 60 / 60 / 24 / 365;
        return lg;
    }

    public static long getTodayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}

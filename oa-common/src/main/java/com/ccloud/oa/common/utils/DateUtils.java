/**
 *
 */
package com.ccloud.oa.common.utils;

import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author breeze
 * @since 2020-08-19
 */
public class DateUtils {

    static final TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式yyyy-MM-dd字符串常量
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 日期格式yyyyMMdd字符串常量
     */
    public static final String DATE_NORMAL_FORMAT = "yyyyMMdd";

    /**
     * 日期格式yyyy-MM字符串常量
     */
    public static final String MONTH_FORMAT = "yyyyMM";

    /**
     * 时间格式是HH:mm:ss字符串常量
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 一天的开始与结束
     */
    public static final String DATETIME_FORMAT_START = "yyyy-MM-dd 00:00:00";
    public static final String DATETIME_FORMAT_END = "yyyy-MM-dd 23:59:59";

    /**
     * 日期格式HH:mm:ss字符串常量
     */
    public static final String HOUR_FORMAT = "HH:mm:ss";

    private static SimpleDateFormat sdf_datetime_format = new SimpleDateFormat(DATETIME_FORMAT);
    private static SimpleDateFormat sdf_date_format = new SimpleDateFormat(DATE_FORMAT);
    private static SimpleDateFormat sdf_hour_format = new SimpleDateFormat(TIME_FORMAT);
    private static SimpleDateFormat sdf_month_format = new SimpleDateFormat(MONTH_FORMAT);

    /**
     * 将时间转为字符串 格式:yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String getDatetimeFormat(Date date) {
        return sdf_datetime_format.format(date);
    }

    /**
     * 将时间转为字符串 格式为：yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        return sdf_date_format.format(date);
    }

    /**
     * 将时间转为字符串 格式: 自定义pattern
     * @param pattern
     * @param date
     * @return
     */
    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获得服务器当前日期及时间，以格式为：yyyy-MM-dd HH:mm:ss的
     * @return
     */
    public static String getNowDateTime() {
        return sdf_datetime_format.format(new Date());
    }

    /**
     * 获得服务器当前日期及时间，以格式为：yyyy-MM-dd
     * @return
     */
    public static String getNowDate() {
        return sdf_date_format.format(new Date());
    }


    /**
     * 获取当前时间
     * @return
     */
    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar = setTimeZone(calendar);
        SimpleDateFormat dfNew = new SimpleDateFormat(DATETIME_FORMAT);
        return dfNew.format(calendar.getTime());
    }

    /**
     * 设置时间
     * @param calendar
     * @return
     */
    public static Calendar setTimeZone(Calendar calendar) {
        TimeZone.setDefault(timeZone);
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar;
    }

    /**
     * 获得服务器当前时间 以格式为：HH:mm:ss的
     * @return 日期字符串形式返回
     */
    public static String getTime() {
        String temp = " ";
        temp += sdf_hour_format.format(new Date());
        return temp;
    }

    /**
     * 获取今天的开始时间 yyyy-MM-dd 00:00:00
     * @return
     */
    public static String getTodayStartTime() {
        return new SimpleDateFormat(DATETIME_FORMAT_START).format(new Date());
    }

    /**
     * 获取今天的结束时间 yyyy-MM-dd 23:59:59
     * @return
     */
    public static String getTodayEndTime() {
        return new SimpleDateFormat(DATETIME_FORMAT_END).format(new Date());
    }

    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    将 日期字符串按
     *                    指定格式转换成
     *                    日期类型
     *                    pattern 指定的日期格式，如:yyyy-MM-dd
     *                    sdate   待转换的日期字符串
     *
     * @Param: [pattern, sdate]
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:44 AM
     */
    public static Date format(String pattern, String sdate) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);;
        Date date = null;
        try {
            date = df.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    格式化数据
     *                    sdate yyyy-MM-dd hh:mm:ss
     *
     *
     * @Param: [sdate]
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:44 AM
     */
    public static Date format(String sdate) {
        Date date = null;
        try {
            date = sdf_datetime_format.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *
     * @param date
     * @param day
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    日期加减
     *                    date 日期
     *                    day 天
     *
     * @Param: [date, day]
     * @return: java.lang.String
     * @Author: share Author
     * @Date: 9:45 AM
     */
    public static String getAddDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.format(date);
        return sdf.format(date);
    }


    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     *
     *
     * @param year
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    得到
     *                    某一年
     *                    周的总数
     *
     * @Param: [year]
     * @return: int
     * @Author: share Author
     * @Date: 9:45 AM
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    /**
     *
     *
     * @param year
     * @param week
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    得到
     *                    某年某周的
     *                    第一天
     *
     *
     *
     * @Param: [year, week]
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:45 AM
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);
        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     *
     *
     * @param year
     * @param week
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    得到
     *                    某年某周的
     *                    最后一天
     *
     * @Param: [year, week]
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:46 AM
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);
        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);
        return getLastDayOfWeek(cal.getTime());
    }

    /**
     *
     *
     * @param date
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    取得
     *                    指定日期
     *                    所在周的
     *                    第一天
     *
     *
     * @Param: [date]
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:46 AM
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }
    /**
     *
     *
     * @param date
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    取得
     *                    指定日期
     *                    所在
     *                    周的
     *                    最后一天
     *
     * @Param: [date]
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:46 AM
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     *
     *
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    取得
     *                    当前日期
     *                    所在
     *                    周的
     *                    第一天
     *
     * @Param: []
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:47 AM
     */
    public static Date getFirstDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }
    /**
     *
     *
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    取得
     *                    当前日期
     *                    所在
     *                    周的
     *                    最后一天
     *
     * @Param: []
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:47 AM
     */
    public static Date getLastDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }
    /**
     * @param day 增加天数
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    增加天数
     *
     * @Param: [date, day]
     * @return: java.util.Date
     * @Author: share Author
     * @Date: 9:47 AM
     */
    public static Date addDay(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    /**
     * 验证时间格式是否是  yyyy-MM-dd 格式
     * @param date
     * @return
     */
    /**
     * @Description: "This method is very strange.
     *                 I advise you to think it over carefully before you change it.
     *                 Unless the God does not know the ghost,
     *                    if you want to know clearly, please see the method described below."
     *                    验证时间
     *                    格式是否是
     *                    yyyy-MM-dd
     *                    格式
     *
     * @Param: [date]
     * @return: boolean
     * @Author: share Author
     * @Date: 9:47 AM
     */
    public static boolean matches(String date) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    /**
     * 比较两个日期相差天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differDays(Date date1, Date date2) {
        SimpleDateFormat df = new SimpleDateFormat(DATETIME_FORMAT_START);
        date1 = DateUtils.format(df.format(date1));
        date2 = DateUtils.format(df.format(date2));
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 比较两个时间相差多少小时 - 当天的
     * @param startTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static double differHours(Date startTime, Date endTime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String startFormat = simpleDateFormat.format(startTime);
        String endFormat = simpleDateFormat.format(endTime);

        Date start = simpleDateFormat.parse(startFormat);
        Date end = simpleDateFormat.parse(endFormat);
        double hours = (double) (end.getTime() - start.getTime()) / (1000 * 3600);
        return Math.floor(hours*10)/10;
    }

    /**
     * 获取月份第一天
     * @param day
     * @return
     */
    public static Date getFisrtDayOfMonth(String day) {
        Calendar cal = getCalendar(day);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某月的最后一天
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param day
     * @param:@return
     * @return:String
     * @throws
     */
    public static Date getLastDayOfMonth(String day) {
        Calendar cal = getCalendar(day);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    private static Calendar getCalendar(String day) {
        int year = Integer.parseInt(day.substring(0, 4));
        int month = Integer.parseInt(day.substring(4, 6));
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        return cal;
    }

    /**
     * 显示日期 天数
     * @param showDay
     * @return
     */
    public static String getDateDay(Date showDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(showDay);
        int weekday = c.get(Calendar.DAY_OF_WEEK) - 1;
        //0 周日 1 2 3 4 5 6 周一到周六
        switch (weekday) {
            case 0:
                return "日";
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
        }
        return "";
    }

    /**
     * 获取最大天数
     * @param monthDate
     * @return
     */
    public static int lastDayOfMonth(Date monthDate) {
        Calendar calendar = getCalendar(DateUtils.format(DATE_NORMAL_FORMAT, monthDate));
        //获取某月最大天数
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取最大天数
     * @param monthDate
     * @return
     */
    public static boolean isFirstDayOfMonth(Date monthDate) {
        if (monthDate == null) {
            return false;
        }
        String dayStirng = DateUtils.format("dd", monthDate);
        return "01".equals(dayStirng);
    }

    /**
     * 是否月份最后一天
     * @param monthDate
     * @return
     */
    public static boolean isLastDayOfMonth(Date monthDate) {
        Calendar calendar = getCalendar(DateUtils.format(DATE_NORMAL_FORMAT, monthDate));
        //获取某月最大天数
        String dayStirng = DateUtils.format("dd", monthDate);
        return dayStirng.equals(calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "");
    }

    /**
     * 获取最大天数
     * @param dateOne
     * @param dateTwo
     * @return
     */
    public static boolean isSameMonth(Date dateOne, Date dateTwo) {
        if (dateOne == null || dateTwo == null) {
            return false;
        }
        return DateUtils.format("MM", dateOne).equals(DateUtils.format("MM", dateTwo));
    }


    /**
     * 获取两个日期的区间列表
     *
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @return
     */
    public static List<Date> getDateDiffList(Date startDate, Date endDate) {
        //日期开始
        List<Date> listDate = new ArrayList<>();

        Date LS = startDate;
        Calendar Date = Calendar.getInstance();//获得通用对象
        Date.setTime(startDate);
        while (LS.getTime() < endDate.getTime()) {
            LS = Date.getTime();
            LS = removeDateHHmmSS(LS);
            listDate.add(LS);
            Date.add(Calendar.DAY_OF_MONTH, 1);//天数加上1
        }
        return listDate;
    }

    /**
     *  获取时间段内的日期列表
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<Date> getDateDiffListWithoutEndTime(Date startTime, Date endTime) {
        List<Date> dateDiffList = DateUtils.getDateDiffList(startTime, endTime);
        if (CollectionUtils.isEmpty(dateDiffList)) {
            return null;
        }
        dateDiffList.remove(dateDiffList.size() - 1);
        return dateDiffList;
    }

    /**
     * 获取两个区间的星期 和每天的日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Map<String, Object>> getDateDiffWeekAndDayNameList(Date startDate, Date endDate) {
        //日期开始
        List<Map<String, Object>> dateList = new ArrayList<>();

        Date LS = startDate;
        Calendar Date = Calendar.getInstance();//获得通用对象
        Date.setTime(startDate);
        Date nowDate = removeDateHHmmSS(new Date());
        while (LS.getTime() < endDate.getTime()) {
            Map<String, Object> map = new HashMap<>();
            LS = Date.getTime();
//			String allWeekName = format("EE", LS);
//			String weekName = allWeekName.substring(2);

            if (LS.compareTo(nowDate) == 0) {
                map.put("isCurrentDay", true);
            }
            map.put("day", format("d", LS));
            map.put("week", getDateDay(LS));
            map.put("date", LS);
            dateList.add(map);
            Date.add(Calendar.DAY_OF_MONTH, 1);//天数加上1
        }
        return dateList;
    }

    /**
     * 将date时分秒变成0
     *
     * @param date
     * @return
     */
    public static Date removeDateHHmmSS(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        return cal1.getTime();
    }

    /**
     * @Description: 比较时间是否在区间内
     * @Author: Mei
     * @date: 2019/10/28 14:23
     * @param startDate
     * @param endDate
     * @param compareDate
     * @exception:
     * @return: boolean
     * @Version: 1.0
     */
    public static boolean compareBelong(Date startDate, Date endDate, Date compareDate) {
        if (compareDate.after(startDate) && compareDate.before(endDate)) {
            return true;
        } else if (compareDate.compareTo(startDate) == 0 || compareDate.compareTo(endDate) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description: 时间段是否有交集
     * @Author: Mei
     * @date: 2019/10/29 20:07
     * @param start
     * @param end
     * @param fromTime
     * @param toTime
     * @exception:
     * @return: boolean
     * @Version: 1.0
     */
    public static boolean comparaWith(Date start, Date end, Date fromTime, Date toTime) {
//		if(!(toTime.before(start) || fromTime.after(end))){
        if (!start.after(toTime) && !end.before(fromTime)) {
            return true;
        } else {
            return false;
        }
    }

}

package util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间处理工具类
 * 
 * @author lihe 2013-7-4 下午5:20:50
 * @see
 * @since
 */
public final class DateUtil {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String FUL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_TIME_PATTERN = DATE_PATTERN + " HH:mm:ss";
    /**
     * 日期格式 年-月-日 时:分:秒， 年份为四位，其他为一位或者两位
     */
    private static final String FULL_DATE_FORMAT = "^(\\d{4})-(0?\\d{1}|1[0-2])-(0?\\d{1}|[12]\\d{1}|3[01])\\s(0?\\d{1}|1\\d{1}|2[0-3]):[0-5]?\\d{1}:([0-5]?\\d{1})$";


    private DateUtil() {

    }

    public static void main(String[] args){
        Calendar day = Calendar.getInstance();
        day.add(Calendar.MONTH,-2);

        System.out.println(DateUtil.formatDate(day,"yyyy-MM"));

    }

    /**
     * 获得当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDate() {
        return getCurrentDate(FORMAT);
    }

    /**
     * 获得当前时间，格式自定义
     * 
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        Calendar day = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(day.getTime());
    }

    /**
     * 获得当前时间减少一个月，格式自定义
     *
     * @param format
     * @return
     */
    public static String getYesterMonthDate(String format) {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(day.getTime());
    }

    /**
     * 获得昨天时间，格式自定义
     * 
     * @param format
     * @return
     */
    public static String getYesterdayDate(String format) {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(day.getTime());
    }

    /**
     * 获取每个月的第一天时间
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1, 0, 0, 0);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return sdf.format(c.getTime());
    }

    /**
     * 获取每个月的最后一天时间
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1, 0, 0, 0);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return sdf.format(c.getTime());
    }

    /**
     * 修复代码质量加的注释，这个方法很绕- -
     * 
     * @author lihe 2013-7-4 下午5:21:33
     * @param date
     * @param format
     * @return
     * @see
     * @since
     */
    public static String formatDate(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        return sdf.format(date);
    }

    /**
     * @author shayankui 2014-6-26 下午5:21:33
     * @param date
     * @param format
     * @return
     * @see
     * @since
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    /**
     * 格式化时间
     * @param calendar
     * @param format
     * @return
     */
    public static String formatDate(Calendar calendar, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }
    /**
     * 按照时间字符串和格式转换成Date类
     * 
     * @author lihe 2013-7-4 下午5:21:50
     * @param date
     * @param format
     * @return
     * @throws ParseException
     * @see
     * @since
     */
    public static Date getDate(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }


    public static Date getDateForTodayStart() {
        return DateUtils.truncate(new Date(), Calendar.DATE);
    }


    /**
     * 获取指定时间,精确到秒(年-月-日 时:分:秒)
     *
     * @param aDate 指定时间
     * @return
     */
    public static String getDateTime(Date aDate) {
        String returnValue = "";

        if (aDate != null) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 注释详见 dateBaseFormat
     * 入参：2014-3-8 8:38:59
     * 出参：2014-03-08 08:38:59
     * @param dateString
     * @return
     */
    public static String formatFullDate(String dateString) {
        return dateBaseFormat(dateString).toString();
    }

    /**
     * 格式化日期，可作为参数校验<br/>
     * 入参格式要求：年-月-日 时:分:秒<br/>
     *
     * 入参：2014-3-8 8:38:59
     * 出参：2014-03-08 08:38:59
     *
     * 入参：2014-3-8 8:38: (或其他错误格式)
     * 出参：null
     *
     * @param dateStr
     * @return
     */
    public static StringBuilder dateBaseFormat(String dateStr) {
        String dateString = validFullDate(dateStr);
        if (dateString == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        Pattern compile = Pattern.compile("([-:\\s]?\\d+)");
        Matcher matcher = compile.matcher(dateString);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (start == 0 || end - start == 3) {
                result.append(dateString.substring(start, end));
                continue;
            }
            char startMark = dateString.charAt(start);
            if ('-' == startMark) {
                result.append("-0").append(dateString.charAt(end - 1));
            } else if (':' == startMark) {
                result.append(":0").append(dateString.charAt(end - 1));
            } else {
                result.append(" 0").append(dateString.charAt(end - 1));
            }
        }
        return result;
    }

    /**
     * 验证日期的正确性，并去除首尾的空白字符
     * 如果返回 null 则日期有误
     * 否则，返回去除首尾空白字符的字符串
     * @param dateStr
     * @return
     */
    public static String validFullDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        String target = dateStr.trim();
        Pattern dateFormat = Pattern.compile(FULL_DATE_FORMAT);
        Matcher dateMatcher = dateFormat.matcher(target);
        return dateMatcher.matches() ? target : null;
    }

    /**
     * 判断两个时间是否跨月
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isCrossMonth(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try
        {
            // 格式化开始日期和结束日期
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(sdf.parse(startTime));

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(sdf.parse(endTime));

            // 开始时间结束时间大小比较
            long start = objCalendarDate1.getTimeInMillis();
            long end = objCalendarDate2.getTimeInMillis();
            if(start-end>0){
                return true;
            }

            int startYear = objCalendarDate1.get(Calendar.YEAR);
            int endYear = objCalendarDate2.get(Calendar.YEAR);

            // 判断是否跨年
            if(startYear-endYear!=0){
                return true;
            }
            // 判断是否跨月
            int startMonth = objCalendarDate1.get(Calendar.MONTH);
            int endMonth = objCalendarDate2.get(Calendar.MONTH);
            if(startMonth-endMonth!=0){
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }



    /**
     * 生成日期
     *
     * @param year
     * @param month
     *            从0开始
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    private static Date parseDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar currentDate = new GregorianCalendar();

        currentDate.set(Calendar.YEAR, year);
        currentDate.set(Calendar.MONTH, month);
        currentDate.set(Calendar.DAY_OF_MONTH, day);
        currentDate.set(Calendar.HOUR_OF_DAY, hour);
        currentDate.set(Calendar.MINUTE, minute);
        currentDate.set(Calendar.SECOND, second);

        return currentDate.getTime();
    }

    /**
     * 转换字符串为时间返回，如果转换不成功则返回默认日期时间
     *
     * @param dateStr
     * @param pattern
     * @param defaultDate
     * @return
     */
    public static Date parseDate(String dateStr, String pattern, Date defaultDate) {

        if (StringUtils.isBlank(dateStr) || "null".equals(dateStr)) {
            return defaultDate;
        }

        DateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            return defaultDate;
        }
    }

    /**
     * 转换字符串为时间返回，如果转换不成功则返回默认日期时间 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @param defaultDate
     * @return
     */
    public static Date parseDate(String dateStr, Date defaultDate) {

        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss", defaultDate);
    }


    /**
     * 获得对应时间的当天结束时间
     *
     * @param
     * @return
     */
    public static Date getDayEnd(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) { // 如果是空则用默认当前时间
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = 23; // 23点
        int minute = 59; // 59分
        int second = 59; // 59秒

        return parseDate(year, month, day, hour, minute, second);
    }

    /**
     * 获得对应时间的当天开始时间
     *
     * @param
     * @return
     */
    public static Date getDayStart(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) { // 如果是空则用默认当前时间
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = 00; // 00点
        int minute = 00; // 00分
        int second = 00; // 00秒

        return parseDate(year, month, day, hour, minute, second);
    }




    /**
     * 获得对应时间的月初时间
     *
     * @param
     * @return
     */
    public static Date getMonthBegain(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) { // 如果是空则用默认当前时间
            calendar.setTime(date);
        } else {
            calendar.setTime(new Date());
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // 从0月开始
        int day = 1;
        int hour = 0; // 0点
        int minute = 0; // 0分
        int second = 0; // 0秒

        return parseDate(year, month, day, hour, minute, second);
    }



    /**
     * 修复开始时间，使之不跨月 如月初1-3号查询当月，则允许跨1个月
     * @param startTime
     * @param endTime
     * @return
     */
    public static Date repairStartTime(Date startTime, Date endTime) {

        Date curDate = new Date();
        if (startTime == null) {
            return curDate;
        } else if (endTime == null) {
            return startTime;
        }

        // 开始年月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        int startYear = calendar.get(Calendar.YEAR);
        int startMonth = calendar.get(Calendar.MONTH);

        // 结束年月
        calendar.setTime(endTime);
        int endYear = calendar.get(Calendar.YEAR);
        int endMonth = calendar.get(Calendar.MONTH);

        // 相隔月数
        int monthDif = endYear * 12 + endMonth - startYear * 12 - startMonth;

        // 当前年月日
        calendar.setTime(curDate);
        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);

        // 是否月初查询当月数据（1——7日）
        boolean isStartMonth = false;
        if ((endYear * 12 + endMonth == curYear * 12 + curMonth) && curDay <= 7) {
            isStartMonth = true;
        }

        if (monthDif < 1) {
            return startTime; // 没有跨月，不需要修复

        } else if (monthDif < 2 && isStartMonth) {
            return startTime; // 虽然跨一个月，但为月初查询，不需要修复

        } else { // 需要修复
            DateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
            StringBuilder dateStr = new StringBuilder(20);
            int year = endYear;
            int month = endMonth;
            if (isStartMonth) { // 月初查询，往后宽限一个月
                if (month == 0) { // 是否年初（1月份）
                    month = 11;
                    year = year - 1;

                } else {
                    month = month - 1;
                }
            }

            int showMonth = month + 1; // 展示效果，将0月修复成1月
            dateStr.append(year).append("-").append(showMonth).append("-01");

            Date retDate = null;
            try {
                retDate = sdf.parse(dateStr.toString());
            } catch (ParseException e) {
                e.printStackTrace();
                retDate = new Date();
            }
            return retDate;
        }

    }

}

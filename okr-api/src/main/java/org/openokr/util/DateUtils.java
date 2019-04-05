package org.openokr.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yuxinzh
 * @create 2019/3/4
 */
public class DateUtils {

    private DateUtils(){}

    public static final String FORMAT_SHORT = "yyyy-MM-dd";
    public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据时间和格式返回字符串
     * @param uDate
     * @param pattern
     * @return
     */
    public static String dateToString(Date uDate, String pattern) {
        return new SimpleDateFormat(pattern).format(uDate);
    }

    /**
     * 将时间转成yyyy-MM-dd格式字符串
     * @param uDate
     * @return
     */
    public static String dateToString(Date uDate) {
        return dateToString(uDate, FORMAT_SHORT);
    }

    /**
     * 将时间转成yyyy-MM-dd HH:mm:ss格式字符串
     * @param uDate
     * @return
     */
    public static String timeToString(Date uDate) {
        return dateToString(uDate, FORMAT_LONG);
    }

    /**
     * 获取当前时间
     * @return
     */
    public static Date getCurrentTime(){
        return new Date();
    }

    /**
     * 获取当前日期字符串
     * @return
     */
    public static String getCurrentDateStr(){
        return dateToString(getCurrentTime());
    }

    /**
     * 获取当前时间字符串
     * @return
     */
    public static String getCurrentTimeStr(){
        return timeToString(getCurrentTime());
    }

    /**
     * 按时间字符串，将时间加N天后，返回新的时间字符串，格式yyyy-MM-dd
     * @param beginDateStr
     * @param day
     * @return
     */
    public static String addDay(String beginDateStr,int day) throws ParseException{
        if (StringUtils.isBlank(beginDateStr)) {
            return null;
        }
        return addDay(beginDateStr,FORMAT_SHORT,day);
    }

    /**
     * 按时间字符串，将时间加N天后，返回新的时间字符串
     * @param beginDateStr
     * @param pattern
     * @param day
     * @return
     */
    public static String addDay(String beginDateStr,String pattern,int day) throws ParseException{
        if (StringUtils.isBlank(beginDateStr) || StringUtils.isBlank(pattern)) {
            return null;
        }
        Date beginDate = stringToDate(beginDateStr,pattern);
        Date endDate = addDay(beginDate,day);
        return dateToString(endDate,pattern);
    }

    /**
     * 将字符串按格式转成时间
     * @param strDate
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strDate, String pattern)
            throws ParseException {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            return null;
        }
        return new SimpleDateFormat(pattern).parse(strDate);
    }

    /**
     * 将字符串按yyyy-MM-dd转成时间
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strDate) throws ParseException{
        return stringToDate(strDate,FORMAT_SHORT);
    }

    /**
     * 将日期增加几天
     * @param beginDate
     * @param day
     * @return
     */
    public static Date addDay(Date beginDate,int day) {
        if (beginDate == null) {
            return null;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(beginDate);
        ca.add(Calendar.DATE, day);
        return ca.getTime();
    }

}

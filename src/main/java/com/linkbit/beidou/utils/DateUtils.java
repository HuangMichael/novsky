package com.linkbit.beidou.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class DateUtils {


    /**
     * @param dateString
     * @param format
     * @return 字符串转集合 Long
     */
    public static Date convertStr2Date(String dateString, String format) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * @param date
     * @param format
     * @return 字符串转集合 Long
     * @throws Exception
     */
    public static String convertDate2Str(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateStr = "";
        try {
            if (date == null) {
                date = new Date();
            }
            dateStr = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }


    /**
     * @param inputDateStr
     * @return 返回次日 00:00:00
     */
    public static Date getCellingDate(String inputDateStr) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date inputDate = null;

        try {
            inputDate = simpleDateFormat.parse(inputDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date outDate = calendar.getTime();

        return outDate;
    }

    /**
     * @param inputDate
     * @return 返回次日 00:00:00
     */
    public static Date getCellingDate(Date inputDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date outDate = calendar.getTime();

        return outDate;
    }


    /**
     * @param inputDate
     * @return 返回次日 00:00:00
     */
    public static Date addDate(Date inputDate, int type, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.add(type, num);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date outDate = calendar.getTime();

        return outDate;
    }

    /**
     * @param beginDate
     * @param endDate   结束日期
     * @param num
     * @param type
     * @return 返回日期list
     */
    public static List<Date> digDateList(Date beginDate, Date endDate, int num, int type) {
        // 0 DAY  1 WEEK 2 MONTH  3 QUARTER  4 YEAR
        List<Date> dateList = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        if (beginDate.after(endDate)) {
            return dateList;
        } else {
            calendar.setTime(beginDate);
            while (calendar.getTime().before(endDate)) {
                switch (type) {
                    case 0:
                        calendar.add(Calendar.DATE, num);
                    case 1:
                        calendar.add(Calendar.DATE, num * 7);
                    case 2:
                        calendar.add(Calendar.MONTH, num);
                    case 3:
                        calendar.add(Calendar.MONTH, num * 3);
                    case 4:
                        calendar.add(Calendar.YEAR, num);
                }
                dateList.add(calendar.getTime());
            }
        }
        System.out.println("dateList------" + dateList.size());
        return dateList;
    }
}

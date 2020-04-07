package com.zjstudio.happly_birthday.tools.common;


import com.zjstudio.happly_birthday.tools.enums.DateEnum;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author 添柴灬少年
 * @date    2018/12/15
 * @version 1.0
 *
 * 方法说明：
 *      1.isDate()  判断一个字符串是否是你想要的时间格式
 *      2.timeDifference()  计算两个时间的时间差，返回你想要的时间格式
 *      3.getAtWillTime()   获取某个时间以前或未来多少的时间
 *      4.passOrFutureTime()    获取某个时间以前或未来多少时间内的日期
 *      5.getTimeIsWeek()   获取某个时间是星期几
 */
public class DateUtil implements Serializable {

    /** 时间格式转换类初始化，采用静态加载
     * 年-月-日   时：分：秒
     */
    public static SimpleDateFormat time01;
    /** 时间格式转换类初始化，采用静态加载
     * 年-月-日
     */
    public static SimpleDateFormat time02;
    /**
     * 年月日
     */
    public static SimpleDateFormat time03;
    /**
     * 时：分：秒
     */
    public static SimpleDateFormat time04;
    /**
     * 时分秒
     */
    public static SimpleDateFormat time05;
    /**
     * 年月日时分秒
     */
    public static SimpleDateFormat time06;
    /**
     * 年
     */
    public static SimpleDateFormat time07;
    /**
     * 年-月
     */
    public static SimpleDateFormat time08;

    static {
        time01 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time02 = new SimpleDateFormat("yyyy-MM-dd");
        time03 = new SimpleDateFormat("yyyyMMdd");
        time04 = new SimpleDateFormat("HH:mm:ss");
        time05 = new SimpleDateFormat("HHmmss");
        time06 = new SimpleDateFormat("yyyyMMddHHmmss");
        time07 = new SimpleDateFormat("yyyy");
        time08 = new SimpleDateFormat("yyyy-MM");
    }

    /**
     * 判断一个字符串是否是你想要的时间格式
     * @param param 你想要的时间格式的时间格式转换类
     * @param str   要判断的字符串
     * @return  true:是      false:不是
     */
    public static boolean isDate(SimpleDateFormat param,String str){
        try{
            return str.equals(param.format(param.parse(str))) ? true : false;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 按你想要的方式计算两个时间差
     * @param param01   第一个时间
     * @param param02   第二个时间
     * @param dateEnum  想要的时间差格式    年、月、日、时、分、秒
     * @return  相差多少
     */
    public static String timeDifference(Date param01, Date param02, DateEnum dateEnum){
        try{
            long nd = 1000 * 24 * 60 * 60;
            param01 = time01.parse(time01.format(param01));
            param02 = time01.parse(time01.format(param02));
            long time = param02.getTime()-param01.getTime();
            switch (dateEnum){
                case Y:
                    return String.valueOf((time / nd)/365);
                case M:
                    return String.valueOf((time / nd)/365 * 12);
                case D:
                    return String.valueOf(time / nd);
                case H:
                    return String.valueOf(time / nd * 24);
                case F:
                    return String.valueOf(time / nd * 24 * 60);
                case S:
                    return String.valueOf(time / nd * 24 * 60 * 60);
                default:
                    return "请输入想要计算的类型";
            }
        }catch (Exception e){
            return "请输入正确的时间";
        }
    }

    /**
     * 获取某个时间过去或者未来多少的时间
     * @param time      某个时间
     * @param param     过去或者未来（过去为负数；未来为正数）
     * @param dateEnum  计算类型
     * @return  时间字符串
     */
    public static String getAtWillTime(String time,int param,DateEnum dateEnum){
        Calendar calendar = Calendar.getInstance();
        Date date ;
        int lo ;
        try{
            date = time01.parse(time);
            calendar.setTime(date);
            switch (dateEnum){
                case Y:
                    lo = calendar.get(Calendar.YEAR);
                    calendar.set(Calendar.YEAR,lo+param);
                    break;
                case M:
                    lo = calendar.get(Calendar.MONTH);
                    calendar.set(Calendar.MONTH,lo+param);
                    break;
                case D:
                    lo = calendar.get(Calendar.DATE);
                    calendar.set(Calendar.DATE,lo+param);
                    break;
                case H:
                    lo = calendar.get(Calendar.HOUR);
                    calendar.set(Calendar.HOUR,lo+param);
                    break;
                case F:
                    lo = calendar.get(Calendar.MINUTE);
                    calendar.set(Calendar.MINUTE,lo+param);
                    break;
                case S:
                    lo = calendar.get(Calendar.SECOND);
                    calendar.set(Calendar.SECOND,lo+param);
                    break;
                    default:
                        return "请传入想要计算的类型";
            }
            return time01.format(calendar.getTime());
        }catch (Exception e){
            return "请传入正确的时间";
        }
    }

    /**
     * 获取某个时间的过去或者未来多少时间内的日期
     * @param time      某个时间
     * @param param     过去或者未来（过去为负数；未来为正数）
     * @param dateEnum  时间类型（多少年、多少月、多少天）
     * @return  时间集合
     */
    public static ArrayList<String> passOrFutureTime(String time,int param,DateEnum dateEnum){
        Date date ;
        ArrayList<String> list = new ArrayList<String>();
        int yearDay = Math.abs(param)*365;
        int monthDay = Math.abs(param)*30;
        int len = 0;
        try{
            date = time01.parse(time);
            switch (dateEnum){
                case Y:
                    len = yearDay;
                    break;
                case M:
                    len = monthDay;
                    break;
                case D:
                    len = Math.abs(param);
                    break;
                    default:
                        break;
            }
            for (int i = 0 ; i < len ; i++){
                int post = param>0 ? i : -i;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + post);
                list.add(time02.format(calendar.getTime()));
            }
        }catch (Exception e){
            return list;
        }
        return list;
    }

    /**
     * 判断某一个时间是星期几
     * @param time  某一个时间
     * @return  周几
     */
    public static int getTimeIsWeek(String time){
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time02.parse(time));
            return calendar.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }catch (Exception e){
            return 0;
        }
    }

}


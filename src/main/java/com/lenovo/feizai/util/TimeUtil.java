package com.lenovo.feizai.util;

import com.lenovo.feizai.entity.Rates;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author feizai
 * @date 2021/4/17 0017 下午 4:02:15
 * @annotation
 */
public class TimeUtil {
    public static String getNowTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
    }

    public static int getTimeNowTo(Timestamp endTime) {
        try {
            Timestamp nowTime = new Timestamp(new Date().getTime());
            long diff = endTime.getTime() - nowTime.getTime();
            long ns = 1000; //1秒钟
            return Integer.valueOf(String.valueOf((diff / ns)));
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getTimeDiff(String start, String end) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;//1天
        long nh = 1000 * 60 * 60;//1小时
        long nm = 1000 * 60; //1分钟
        try {
            // long ns = 1000;
            // 获得两个时间的毫秒时间差异
            long diff = simpleFormat.parse(end).getTime() - simpleFormat.parse(start).getTime();
            // 计算差多少分钟
            long min = diff / nm;
            return min;
        } catch (Exception e) {
            return 0;
        }
    }

    public static float getPrice(String start, String end, Rates rates) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;//1天
        long nh = 1000 * 60 * 60;//1小时
        long nm = 1000 * 60; //1分钟
        float price = 0.0f;
        try {
            // long ns = 1000;
            // 获得两个时间的毫秒时间差异
            long diff = simpleFormat.parse(end).getTime() - simpleFormat.parse(start).getTime();
            // 计算差多少小时
            long hour = diff / nh;
            // 计算差多少分钟
            long min = diff % nh / nm;
            System.out.println(hour + "hour" + min + "min");
            if (hour == 0) {
                price = rates.getOnehour();
            } else {
                if (hour == 1) {
                    if (min >= 30) {
                        price = rates.getOnehour() + rates.getOtherone() * 2;
                    } else {
                        price = rates.getOnehour() + rates.getOtherone();
                    }
                } else {
                    if (min >= 30) {
                        price = rates.getOnehour() + rates.getOtherone() * (hour - 1) * 2 + rates.getOtherone() * 2;
                    } else {
                        price = rates.getOnehour() + rates.getOtherone() * (hour - 1) * 2 + rates.getOtherone();
                    }
                }
            }
        } catch (Exception e) {

        }
        return price;
    }
}

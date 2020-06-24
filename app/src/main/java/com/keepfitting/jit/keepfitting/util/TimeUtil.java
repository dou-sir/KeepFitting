package com.keepfitting.jit.keepfitting.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 顾志豪 on 2020/6/23.
 */

public class TimeUtil {

    public static String timestampToData(long timestamp){

        String date = new java.text.SimpleDateFormat("MM-dd").format(new java.util.Date(timestamp));
        return date;
    }

    public static String timestampToYMD(long timestamp){

        String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
        return date;
    }




    /**
     * 计算年龄
     */
    public static int BirthDayToAge(String birthDayStr) throws Exception {

        // 如果为空，返回
        if (TextUtils.isEmpty(birthDayStr)) {
            return 0;
        }

        Date birthDay;
        // 如果不是我们想要的类型，返回
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            birthDay = formatter.parse(birthDayStr);
        } catch (Exception ex) {
            return 0;
        }


        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH)+1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        }

        return age;
    }
}

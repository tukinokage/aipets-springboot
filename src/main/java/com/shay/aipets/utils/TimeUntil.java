package com.shay.aipets.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUntil {
    public static String getDateTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String time = formatter.format(date);

        return time;
    }

    public static String getDate(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String time = formatter.format(date);

        return time;
    }


}

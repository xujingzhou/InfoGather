package com.dten.punchinghole.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConcurrentDateUtil {

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static Date parse(String dateStr) throws ParseException {
        if (StringUtils.isNotEmpty(dateStr)) {
            return threadLocal.get().parse(dateStr);
        } else {
            return null;
        }
    }

    public static String format(Date date) {
        if (date != null) {
            return threadLocal.get().format(date);
        } else {
            return "";
        }
    }
}

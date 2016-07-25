package jp.pycon.pyconjp2016app.Util;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rhoboro on 7/25/16.
 */
public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DISPLAY_DATE_FORMAT = "yyyy/MM/dd HH:mm";
    private static final String DUMMY_DATE = "2016-09-21";

    @NonNull
    public static String toTimeFormattedString(String time) {
        java.text.DateFormat df1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        java.text.DateFormat df2 = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        try {
            Date date = df1.parse(DUMMY_DATE + " " + time);
            return df2.format(date);
        } catch (ParseException e) {
            // nop
        }
        return "";
    }

    public static String toStartToEndFormattedString(String day, String start, String end) {
        java.text.DateFormat df1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        java.text.DateFormat df2 = new SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.US);
        java.text.DateFormat df3 = new SimpleDateFormat(TIME_FORMAT, Locale.US);
        try {
            Date startDate = df1.parse(day + " " + start);
            Date endDate = df1.parse(day + " " + end);
            String startStr = df2.format(startDate);
            String endStr = df3.format(endDate);
            return startStr + " - " + endStr;
        } catch (ParseException e) {
            // nop
        }
        return "";
    }

}

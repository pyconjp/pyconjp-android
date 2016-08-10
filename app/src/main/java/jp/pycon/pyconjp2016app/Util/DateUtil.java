package jp.pycon.pyconjp2016app.Util;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by rhoboro on 7/25/16.
 */
public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TIME_FORMAT = "HH:mm";
    private static final String DISPLAY_DATE_FORMAT = "yyyy/MM/dd HH:mm";
    private static final String DUMMY_DATE = "2016-09-21";

    /**
     * 13:00の書式の文字列で返します
     * @param time 13:00:00の書式文字列
     * @return 13:00の書式文字列
     */
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

    /**
     * 2016/09/21 13:00 - 13:30の書式の文字列で返します
     * @param day 2016-09-21の書式文字列
     * @param start 13:00:00の書式文字列
     * @param end 13:30:00の書式文字列
     * @return 2016/09/21 13:00 - 13:30の書式文字列
     */
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

    /**
     * 通知をする時間を返します
     * @param day 通知をするトークのある日
     * @param start 通知をするトークの開始時刻
     * @param duration 何分前か
     * @return 通知をする時間
     */
    public static long toNotificationMills(String day, String start, int duration) {
        Date date = null;
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.JAPAN);
        try {
            date = df.parse(day + " " + start);
        } catch (ParseException e) {
            //nop
        }
        if (date != null) {
            return date.getTime() - TimeUnit.MINUTES.toMillis(duration);
        }
        return 0;
    }

}

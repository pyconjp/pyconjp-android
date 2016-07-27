package jp.pycon.pyconjp2016app.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.TimeZone;

import jp.pycon.pyconjp2016app.BroadcastReceiver.NotificationReceiver;

/**
 * Created by rhoboro on 7/26/16.
 */
public class NotificationUtil {

    public static void setNotification(Context context, String title, int pk, int interval) {

        final Intent intent = NotificationReceiver.makeNotificationIntent(context, pk, title);
        final PendingIntent sender = PendingIntent.getBroadcast(context,  pk, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);
//        calendar.setTimeZone(TimeZone.getDefault());
//        calendar.set(Calendar.HOUR_OF_DAY, 17);
//        calendar.set(Calendar.MINUTE, 30);

        final AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

    }

    public static void cancelNotification(Context context, int pk) {
        final Intent intent = new Intent(context, NotificationReceiver.class);
        final PendingIntent sender = PendingIntent.getBroadcast(context, pk, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

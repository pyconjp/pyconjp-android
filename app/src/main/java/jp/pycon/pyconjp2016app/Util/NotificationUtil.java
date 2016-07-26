package jp.pycon.pyconjp2016app.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import jp.pycon.pyconjp2016app.BroadcastReceiver.NotificationReceiver;

/**
 * Created by rhoboro on 7/26/16.
 */
public class NotificationUtil {

    public static void setNotification(Context context, String message, int pk, int interval) {

        final Intent intent = NotificationReceiver.makeNotificationIntent(context, message);
        final PendingIntent sender = PendingIntent.getBroadcast(context,  pk, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, interval);

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

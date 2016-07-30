package jp.pycon.pyconjp2016app.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import io.realm.Realm;
import jp.pycon.pyconjp2016app.BroadcastReceiver.NotificationReceiver;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationDetailObject;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/26/16.
 */
public class NotificationUtil {

    public static void setNotification(Context context, int pk) {

        Realm realm = Realm.getDefaultInstance();
        RealmPresentationDetailObject obj = realm
                .where(RealmPresentationDetailObject.class)
                .equalTo("pk", pk)
                .findFirst();

        Intent intent = NotificationReceiver.makeNotificationIntent(context, pk, obj.title);
        PendingIntent sender = PendingIntent.getBroadcast(context, pk, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // TODO: 通知設定でとった時間にする
        long mills = DateUtil.toNotificationMills(obj.day, obj.start, 1);
        if (mills != 0) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, mills, sender);
        } else {
            Toast.makeText(context, context.getString(R.string.notification_failed_message), Toast.LENGTH_SHORT).show();
        }
        realm.close();
    }

    public static void cancelNotification(Context context, int pk) {
        final Intent intent = new Intent(context, NotificationReceiver.class);
        final PendingIntent sender = PendingIntent.getBroadcast(context, pk, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

package jp.pycon.pyconjp2016app.BroadcastReceiver;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import jp.pycon.pyconjp2016app.Feature.Talks.Detail.TalkDetailActivity;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/26/16.
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final String BUNDLE_KEY_TITLE = "bundle_key_title";
    private static final String BUNDLE_KEY_PK = "bundle_key_pk";

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(BUNDLE_KEY_TITLE);
        final int pk = intent.getIntExtra(BUNDLE_KEY_PK, 0);
        final Intent i = new Intent(context, TalkDetailActivity.class);
        i.putExtra(TalkDetailActivity.BUNDLE_KEY_PRESENTATION_ID, pk);
        i.setAction("intent_action");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, pk, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_icon);
        Builder builder = new Builder(context);
        builder.setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.notification_message, title))
                .setLargeIcon(icon)
                .setSmallIcon(R.drawable.ic_stat_logo_icon)
                .setTicker(context.getString(R.string.notification_message, title))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(pk, builder.build());
    }

    public static Intent makeNotificationIntent(Context context, int pk, String title) {
        final Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(BUNDLE_KEY_TITLE, title);
        intent.putExtra(BUNDLE_KEY_PK, pk);
        return intent;
    }
}

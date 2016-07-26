package jp.pycon.pyconjp2016app.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rhoboro on 7/26/16.
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final String BUNDLE_KEY_MESSAGE = "bundle_key_message";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(BUNDLE_KEY_MESSAGE);
        Log.d("hoge", message);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent makeNotificationIntent(Context context, String message) {
        final Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(BUNDLE_KEY_MESSAGE, message);
        return intent;
    }
}

package jp.pycon.pyconjp2017app.Util;

import android.content.Context;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import jp.pycon.pyconjp2017app.App;

/**
 * Created by rhoboro on 9/16/16.
 */
public class GAUtil {

    public static void sendCommonScreen(Context context, String title) {
        App application = (App)context.getApplicationContext();
        Tracker tracker = application.getDefaultTracker();
        tracker.setScreenName(title);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static void sendTalkDetail(Context context, String title) {
        String sendTitle = "Detail: " + title;
        sendCommonScreen(context, sendTitle);
    }

    public static void sendAbout(Context context, String title) {
        String sendTitle = "About: " + title;
        sendCommonScreen(context, sendTitle);
    }

    public static void sendEvent(Context context, String title) {
        String sendTitle = "Event: " + title;
        sendCommonScreen(context, sendTitle);
    }

    public static void sendFloorMap(Context context, String title) {
        String sendTitle = "FloorMap: " + title;
        sendCommonScreen(context, sendTitle);
    }
}

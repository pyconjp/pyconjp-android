package jp.pycon.pyconjp2016app.Util;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import jp.pycon.pyconjp2016app.Feature.Feature;

/**
 * Created by rhoboro on 9/15/16.
 */
public class FirebaseUtil {

    /**
     * ナビゲーションドロワーのメニューを選択したときのイベント
     * @param context context
     * @param feature 選択されたメニュー
     */
    public static void sendNavItemClicked(Context context, Feature feature) {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, "navigation_item");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, feature.getPageName());
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}

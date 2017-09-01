package jp.pycon.pyconjp2017app.Util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhoboro on 9/15/16.
 */
public class FirebaseUtil {

    public static void initialize(final Context context) {
        final FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);
        final FirebaseRemoteConfig mRemoteConfig = FirebaseRemoteConfig.getInstance();
        // Mapで直接デフォルト値を差し込む
        Map<String, Object> defValues = new HashMap<>();
        defValues.put("enable_survey", false);
        mRemoteConfig.setDefaults(defValues);
        // 5回/1hの制限があるようなので12分に一回更新
        mRemoteConfig.fetch(60 * 60 / 5)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mRemoteConfig.activateFetched();
                        }
                    }
                });
    }

    public static boolean getEnableSurvey() {
        final FirebaseRemoteConfig mRemoteConfig = FirebaseRemoteConfig.getInstance();
        return mRemoteConfig.getBoolean("enable_survey") && !TextUtils.isEmpty(FirebaseUtil.getSurveyUrl());
    }

    public static String getSurveyUrl() {
        final FirebaseRemoteConfig mRemoteConfig = FirebaseRemoteConfig.getInstance();
        return mRemoteConfig.getString("survey_url");
    }

    /**
     * ナビゲーションドロワーのメニューを選択したときのイベント
     * @param context context
     * @param title 選択されたメニュー
     */
    public static void sendNavItemClicked(Context context, String title) {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, "navigation_item");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public static void sendTalkDetail(Context context, String title, String speaker, String start) {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, "show_talk_detail");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
        bundle.putString(FirebaseAnalytics.Param.CHARACTER, speaker);
        bundle.putString(FirebaseAnalytics.Param.START_DATE, start);
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public static void sendEvent(Context context, String title) {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, "show_event_detail");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public static void sendFloorMap(Context context, String title) {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, "show_floor_map_detail");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    public static void sendAbout(Context context, String title) {
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.LOCATION, "show_about");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }
}

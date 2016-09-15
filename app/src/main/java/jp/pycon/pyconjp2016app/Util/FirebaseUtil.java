package jp.pycon.pyconjp2016app.Util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

import jp.pycon.pyconjp2016app.Feature.Feature;

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
        mRemoteConfig.fetch(60)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mRemoteConfig.activateFetched();
                            Log.d("hoge", "enable_survey: " + getEnableSurvey(context));
                        }
                    }
                });
    }

    public static boolean getEnableSurvey(Context context) {
        final FirebaseRemoteConfig mRemoteConfig = FirebaseRemoteConfig.getInstance();
        return mRemoteConfig.getBoolean("enable_survey");
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
}

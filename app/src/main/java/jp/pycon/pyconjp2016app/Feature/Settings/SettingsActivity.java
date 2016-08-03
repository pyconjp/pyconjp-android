package jp.pycon.pyconjp2016app.Feature.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jp.pycon.pyconjp2016app.BaseAppCompatActivity;

/**
 * Created by rhoboro on 7/31/16.
 */
public class SettingsActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

}

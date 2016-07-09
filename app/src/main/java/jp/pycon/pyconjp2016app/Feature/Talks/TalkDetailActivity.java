package jp.pycon.pyconjp2016app.Feature.Talks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/9/16.
 */
public class TalkDetailActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_TALK_ID = "bundle_key_talk_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail);
    }
}

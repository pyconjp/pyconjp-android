package jp.pycon.pyconjp2016app.Feature.Talks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationDetailObject;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/9/16.
 */
public class TalkDetailActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_PRESENTATION_ID = "bundle_key_presentation_id";
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail);
        realm = Realm.getDefaultInstance();
        int pk = getIntent().getIntExtra(BUNDLE_KEY_PRESENTATION_ID, 0);
        RealmResults<RealmPresentationDetailObject> results = realm.where(RealmPresentationDetailObject.class)
                .equalTo("pk", pk)
                .findAll();
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(results.get(0).title);
        // スピーカー
        ((TextView)findViewById(R.id.speaker)).setText(results.get(0).speakerstring());
        // 説明
        ((TextView)findViewById(R.id.description)).setText(results.get(0).description);
        // 概要
        ((TextView)findViewById(R.id.abst)).setText(results.get(0).abst);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

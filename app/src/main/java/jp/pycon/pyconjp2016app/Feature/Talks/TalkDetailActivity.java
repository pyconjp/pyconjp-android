package jp.pycon.pyconjp2016app.Feature.Talks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

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

        String title = getIntent().getStringExtra(BUNDLE_KEY_TALK_ID);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

package jp.pycon.pyconjp2016app.Feature.Talks.Detail;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationDetailObject;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.Util.PreferencesManager;

/**
 * Created by rhoboro on 7/9/16.
 */
public class TalkDetailActivity extends AppCompatActivity {
    public static final String BUNDLE_KEY_PRESENTATION_ID = "bundle_key_presentation_id";
    private Realm realm;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail);
        realm = Realm.getDefaultInstance();
        mHandler = new Handler(Looper.getMainLooper());
        initToolbar();

        final int pk = getIntent().getIntExtra(BUNDLE_KEY_PRESENTATION_ID, 0);
        RealmResults<RealmPresentationDetailObject> results = realm.where(RealmPresentationDetailObject.class)
                .equalTo("pk", pk)
                .findAll();
        final RealmPresentationDetailObject presentation = results.get(0);

        // ビューの設定
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(presentation.title);
        setupViews(presentation);
        setupBookmark(pk);
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

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupViews(RealmPresentationDetailObject presentation) {
        // ロゴ
        final TypedArray logos = getResources().obtainTypedArray(R.array.python_logo);
        Random r = new Random();
        int i = r.nextInt(6);
        Drawable drawable = logos.getDrawable(i);
        ((ImageView)findViewById(R.id.python_logo)).setImageDrawable(drawable);
        // スピーカー
        ((TextView)findViewById(R.id.speaker)).setText(presentation.speakerstring());
        // 説明
        ((TextView)findViewById(R.id.description)).setText(presentation.description);
        // 概要
        ((TextView)findViewById(R.id.abst)).setText(presentation.abst);
    }

    private void setupBookmark(final int pk) {
        final FloatingActionButton bookmark = (FloatingActionButton)findViewById(R.id.bookmark);
        if (PreferencesManager.isBookmarkContains(getApplicationContext(), pk)) {
            bookmark.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bookmark_black_24dp, null));
        }
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookmarkDialog dialog = new BookmarkDialog();
                dialog.setmListener(new BookmarkDialog.BookmarkDialogListener() {
                    @Override
                    public void bookmarkStatusChanged(int pk2, boolean added) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (PreferencesManager.isBookmarkContains(getApplicationContext(), pk)) {
                                    bookmark.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bookmark_black_24dp, null));
                                } else {
                                    bookmark.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_bookmark_border_black_24dp, null));
                                }
                            }
                        });
                    }
                });
                Bundle bundle = new Bundle();
                // TODO: ブックマーク状態の変更でブックマーク画像を変更させる
                bundle.putInt(BookmarkDialog.BUNDLE_KEY_PRESENTATION_ID, pk);
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "bookmark");
            }
        });
    }
}

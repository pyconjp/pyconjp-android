package jp.pycon.pyconjp2016app.Feature.Talks.Detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.Random;

import io.realm.Realm;
import jp.pycon.pyconjp2016app.API.Client.APIClient;
import jp.pycon.pyconjp2016app.App;
import jp.pycon.pyconjp2016app.BaseAppCompatActivity;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationDetailEntity;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationDetailObject;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.Util.ColorUtil;
import jp.pycon.pyconjp2016app.Util.PreferencesManager;
import jp.pycon.pyconjp2016app.Util.RealmUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rhoboro on 7/9/16.
 */
public class TalkDetailActivity extends BaseAppCompatActivity {
    public static final String BUNDLE_KEY_PRESENTATION_ID = "bundle_key_presentation_id";
    private Realm realm;
    private Handler mHandler;

    public static void start(Context context, int pk) {
        final Intent intent = new Intent(context, TalkDetailActivity.class);
        intent.putExtra(TalkDetailActivity.BUNDLE_KEY_PRESENTATION_ID, pk);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        realm = Realm.getDefaultInstance();
        mHandler = new Handler(Looper.getMainLooper());
        initToolbar();

        final int pk = getIntent().getIntExtra(BUNDLE_KEY_PRESENTATION_ID, 0);
        Log.d("pk", "" + pk);
        if (RealmUtil.isTalkDetailExist(realm, pk)) {
            showTalkDetail(pk);
        } else {
            getPyConJPPresentationDetail(pk);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /**
     * ツールバーの初期化処理
     */
    private void initToolbar() {
        final Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * トーク詳細情報の表示処理
     * @param pk 表示するトークのPK
     */
    private void showTalkDetail(int pk) {
        final RealmPresentationDetailObject presentation = RealmUtil.getTalkDetail(realm, pk);
        // ビューの設定
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(presentation.title);
        setupViews(presentation);
        // ブックマークは一度機能を外す
//        setupBookmark(pk);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        findViewById(R.id.detail_view).setVisibility(View.VISIBLE);
    }

    /**
     * ビューの初期化
     * @param presentation 表示するトークのRealmオブジェクト
     */
    private void setupViews(RealmPresentationDetailObject presentation) {
        // 日時
        final String dispDate = presentation.dispDate;
        if (TextUtils.isEmpty(dispDate)) {
            findViewById(R.id.date_view).setVisibility(View.GONE);
        } else {
            TextView dateView = (TextView)findViewById(R.id.day_start_end);
            dateView.setText(dispDate);
        }
        // 部屋
        final String room = presentation.rooms;
        if (TextUtils.isEmpty(room)) {
            findViewById(R.id.room_view).setVisibility(View.GONE);
            findViewById(R.id.hashTag).setVisibility(View.GONE);
        } else {
            TextView roomView = (TextView)findViewById(R.id.room);
            roomView.setText(room);
            roomView.setTextColor(ContextCompat.getColor(this, ColorUtil.getRoomColor(room)));
            TextView hashTag = (TextView)findViewById(R.id.hashTag);
            hashTag.setText(hashTagString(room));
        }
        ((TextView)findViewById(R.id.level)).setText(presentation.level);
        ((TextView)findViewById(R.id.category)).setText(presentation.category);
        // ロゴ
        final TypedArray logos = getResources().obtainTypedArray(R.array.python_logo);
        Drawable drawable = logos.getDrawable(ColorUtil.getLogoColorIndex(room));
        ((ImageView)findViewById(R.id.python_logo)).setImageDrawable(drawable);
        // スピーカー
        ((TextView)findViewById(R.id.speaker)).setText(presentation.speakerstring());
        // 説明
        ((TextView)findViewById(R.id.description)).setText(presentation.description);
        // 概要
        ((TextView)findViewById(R.id.abst)).setText(presentation.abst);
    }

    /**
     * ブックマークボタンの初期化
     * @param pk 表示するトークのPK
     */
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
                                boolean bookmarkStatus = PreferencesManager.isBookmarkContains(getApplicationContext(), pk);
                                int resId = bookmarkStatus ? R.drawable.ic_bookmark_black_24dp : R.drawable.ic_bookmark_border_black_24dp;
                                bookmark.setImageDrawable(ResourcesCompat.getDrawable(getResources(), resId, null));
                            }
                        });
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putInt(BookmarkDialog.BUNDLE_KEY_PRESENTATION_ID, pk);
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "bookmark");
            }
        });
    }

    /**
     * 表示するトークの詳細情報を取得します
     * @param pk 表示するトークのPK
     */
    private void getPyConJPPresentationDetail(final int pk) {
        APIClient apiClient = ((App)getApplication()).getAPIClient();
        rx.Observable<PresentationDetailEntity> observable = apiClient.getPyConJPPresentationDetail(pk);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PresentationDetailEntity>() {
                               @Override
                               public void onCompleted() {
                                   showTalkDetail(pk);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   Toast.makeText(getApplicationContext(), "error" + e, Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onNext(final PresentationDetailEntity presentation) {
                                   RealmUtil.savePresentationDetail(realm, pk, presentation);
                               }
                           }
                );
    }

    /**
     * ハッシュタグがタップされた時に呼ばれます
     * @param view タップされたビュー
     */
    public void onHashTagClicked(View view) {
        String hashTag = ((TextView)view).getText().toString().replaceAll("#", "");
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://search?query=%23" + hashTag));
            startActivity(intent);

        }catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://mobile.twitter.com/search?q=%23" + hashTag + "&s=typd")));
        }
    }

    /**
     * 部屋番号からハッシュタグ文字列を生成
     * @param room 部屋番号
     * @return ハッシュタグ
     */
    private String hashTagString(String room) {
        String prefix = getString(R.string.hashtag_prefix);
        int num = Integer.parseInt(room.replaceAll("[^0-9]",""));
        return prefix+Integer.toString(num);
    }
}

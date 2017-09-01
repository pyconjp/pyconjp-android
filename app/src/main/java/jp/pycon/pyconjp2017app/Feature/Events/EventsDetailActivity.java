package jp.pycon.pyconjp2017app.Feature.Events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jp.pycon.pyconjp2017app.BaseAppCompatActivity;
import jp.pycon.pyconjp2017app.R;
import jp.pycon.pyconjp2017app.Util.FirebaseUtil;
import jp.pycon.pyconjp2017app.Util.GAUtil;

/**
 * Created by rhoboro on 8/28/16.
 */
public class EventsDetailActivity extends BaseAppCompatActivity {

    private static final String BUNDLE_KEY_TITLE = "bundle_key_title";
    private static final String BUNDLE_KEY_URL = "bundle_key_url";

    public static void start(Context context, int title, int url) {

        final Intent intent = new Intent(context, EventsDetailActivity.class);
        intent.putExtra(BUNDLE_KEY_TITLE, title);
        intent.putExtra(BUNDLE_KEY_URL, url);
        context.startActivity(intent);

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);

        final int titleResId = getIntent().getIntExtra(BUNDLE_KEY_TITLE, 0);
        ActionBar bar = getSupportActionBar();
        bar.setTitle(titleResId);

        FirebaseUtil.sendEvent(this, getString(titleResId));
        GAUtil.sendEvent(this, getString(titleResId));

        final int urlResId = getIntent().getIntExtra(BUNDLE_KEY_URL, 0);
        final String url = getString(urlResId);

        final WebView webView = (WebView)findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
    }
}

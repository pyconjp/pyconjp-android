package jp.pycon.pyconjp2017app.Feature.About;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jp.pycon.pyconjp2017app.BaseAppCompatActivity;
import jp.pycon.pyconjp2017app.R;

/**
 * Created by rhoboro on 9/6/16.
 */
public class AboutSponsorActivity extends BaseAppCompatActivity {

    private static final String SPONSOR_PAGE_URL = "https://pycon.jp/2017/ja/sponsors/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        // ライセンスページの表示
        final WebView webView = (WebView)findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(SPONSOR_PAGE_URL);
        webView.getSettings().setJavaScriptEnabled(true);
    }
}

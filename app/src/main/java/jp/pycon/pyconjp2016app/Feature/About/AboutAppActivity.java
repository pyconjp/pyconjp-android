package jp.pycon.pyconjp2016app.Feature.About;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jp.pycon.pyconjp2016app.BaseAppCompatActivity;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 5/8/16.
 */
public class AboutAppActivity extends BaseAppCompatActivity {

    private static final String LICENSE_PAGE_URL = "file:///android_asset/licenses.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        // ライセンスページの表示
        final WebView webView = (WebView)findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(LICENSE_PAGE_URL);
        webView.getSettings().setJavaScriptEnabled(true);
    }
}

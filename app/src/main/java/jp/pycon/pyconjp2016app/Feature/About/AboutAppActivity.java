package jp.pycon.pyconjp2016app.Feature.About;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.databinding.ActivityAboutOrganizerBinding;

/**
 * Created by rhoboro on 5/8/16.
 */
public class AboutAppActivity extends AppCompatActivity {

    private static final String LICENSE_PAGE_URL = "https://pyconjp.github.io/pyconjp-android/license.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        // ライセンスページの表示
        final WebView webView = (WebView)findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(LICENSE_PAGE_URL);
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

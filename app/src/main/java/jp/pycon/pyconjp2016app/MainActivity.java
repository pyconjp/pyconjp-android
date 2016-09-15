package jp.pycon.pyconjp2016app;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import jp.pycon.pyconjp2016app.Feature.About.AboutSponsorActivity;
import jp.pycon.pyconjp2016app.Feature.Events.EventsDetailActivity;
import jp.pycon.pyconjp2016app.Feature.Feature;
import jp.pycon.pyconjp2016app.Feature.Talks.List.TalksFragment;
import jp.pycon.pyconjp2016app.Util.FirebaseUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    private static final long DRAWER_CLOSE_DELAY_MILLS = 300L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        // Firebase Analyticsを初期化
        FirebaseUtil.initialize(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.getMenu().findItem(R.id.nav_survey).setVisible(FirebaseUtil.getEnableSurvey());
            navigationView.setNavigationItemSelectedListener(this);
            if (savedInstanceState == null) {
                // 初期表示はトーク一覧画面
                replaceFragment(TalksFragment.newInstance());
            }
        }
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return;
        }
        super.onBackPressed();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (!item.isChecked()) {
            if (item.getItemId() == R.id.nav_survey) {
                FirebaseUtil.sendNavItemClicked(this, item.getTitle().toString());
                showSurvey();
            } else {
                item.setChecked(true);
                Feature feature = Feature.forMenuId(item.getItemId());
                FirebaseUtil.sendNavItemClicked(this, feature.getPageName());
                changePage(feature.createFragment());
            }
        }

        return true;
    }

    private void changePage(final Fragment fragment) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                replaceFragment(fragment);
            }
        }, DRAWER_CLOSE_DELAY_MILLS);
    }

    private void toggleToolbarElevation(boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float elevation = enable ? getResources().getDimension(R.dimen.elevation) : 0;
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                toolbar.setElevation(elevation);
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);
        ft.replace(R.id.main_view, fragment, fragment.getClass().getSimpleName());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackStackChanged() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment current = fragmentManager.findFragmentById(R.id.main_view);
        if (current == null) {
            // no more fragments in the stack. finish.
            finish();
            return;
        }
        Feature feature = Feature.forName(current);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setCheckedItem(feature.getMenuId());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toggleToolbarElevation(feature.shouldToggleToolbar());
        if (toolbar != null) {
            toolbar.setTitle(feature.getTitleResId());
        }
    }

    private void showSurvey() {
        new Handler().postDelayed(() -> {
            CustomTabsIntent intent = new CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .build();

            intent.launchUrl(this, Uri.parse(getString(R.string.survey_url)));
        },DRAWER_CLOSE_DELAY_MILLS);
    }

}

package jp.pycon.pyconjp2016app;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import jp.pycon.pyconjp2016app.API.Client.APIClient;
import jp.pycon.pyconjp2016app.API.Entity.PyConJPScheduleEntity;
import jp.pycon.pyconjp2016app.Feature.About.AboutFragment;
import jp.pycon.pyconjp2016app.Feature.Access.AccessFragment;
import jp.pycon.pyconjp2016app.Feature.Feature;
import jp.pycon.pyconjp2016app.Feature.Talks.MyTalksFragment;
import jp.pycon.pyconjp2016app.Feature.Talks.TalksFragment;

import jp.pycon.pyconjp2016app.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {

    private static final long DRAWER_CLOSE_DELAY_MILLS = 300L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
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

        getPyConJPSchedule();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            if (savedInstanceState == null) {
                // 初期表示はトーク一覧画面
                replaceFragment(TalksFragment.newInstance());
            }
        }
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void getPyConJPSchedule() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pycon.jp/2016/site_media/static/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIClient apiClient = retrofit.create(APIClient.class);
        rx.Observable<PyConJPScheduleEntity> observable =  apiClient.getPyConJPSchedule();
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PyConJPScheduleEntity>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onNext(PyConJPScheduleEntity pyConJPScheduleEntity) {
                                   Log.d("tag", "API" + pyConJPScheduleEntity.data0.contents.toString());
                               }
                           }
                );
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if (!item.isChecked()) {
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.nav_talks:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replaceFragment(TalksFragment.newInstance());
                        }
                    }, DRAWER_CLOSE_DELAY_MILLS);
                    break;
                case R.id.nav_my_talks:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replaceFragment(MyTalksFragment.newInstance());
                        }
                    }, DRAWER_CLOSE_DELAY_MILLS);
                    break;
                case R.id.nav_access:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replaceFragment(AccessFragment.newInstance());
                        }
                    }, DRAWER_CLOSE_DELAY_MILLS);
                    break;
                case R.id.nav_about:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replaceFragment(AboutFragment.newInstance());
                        }
                    }, DRAWER_CLOSE_DELAY_MILLS);
                    break;
                default:
                    break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
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
}

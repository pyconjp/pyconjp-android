package jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.Adapter.AboutPagerAdapter;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.databinding.ActivityAboutOrganizerBinding;

/**
 * Created by rhoboro on 5/8/16.
 */
public class AboutOrganizerActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAboutOrganizerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about_organizer);
        TabLayout tabLayout = binding.tagLayout;

        ViewPager viewPager = binding.viewPager;
        AboutPagerAdapter aboutPagerAdapter = new AboutPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(aboutPagerAdapter);
        viewPager.addOnPageChangeListener(aboutPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setElevation(0f);
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

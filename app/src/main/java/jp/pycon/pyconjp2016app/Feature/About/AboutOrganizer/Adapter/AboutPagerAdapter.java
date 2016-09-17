package jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.ConductFragment;
import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.EventOutlineFragment;
import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.StaffListFragment;
import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.ThemeFragment;
import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.WhatIsFragment;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.Util.FirebaseUtil;
import jp.pycon.pyconjp2016app.Util.GAUtil;

/**
 * Created by wj on 16/8/3.
 */

public class AboutPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private Context context;

    public AboutPagerAdapter(FragmentManager fm,  Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WhatIsFragment();
            case 1:
                return new StaffListFragment();
            case 2:
                return new ConductFragment();
            case 3:
                return new EventOutlineFragment();
            case 4:
                return new ThemeFragment();
        }
        return new WhatIsFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.about_tab1);
            case 1:
                return context.getString(R.string.about_tab2);
            case 2:
                return context.getString(R.string.about_tab3);
            case 3:
                return context.getString(R.string.about_tab4);
            case 4:
                return context.getString(R.string.about_tab5);
        }
        return "";
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = context.getString(R.string.about_tab1);
                break;
            case 1:
                title = context.getString(R.string.about_tab2);
                break;
            case 2:
                title = context.getString(R.string.about_tab3);
                break;
            case 3:
                title = context.getString(R.string.about_tab4);
                break;
            case 4:
                title = context.getString(R.string.about_tab5);
                break;
        }
        FirebaseUtil.sendAbout(context, title);
        GAUtil.sendAbout(context, title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public int getCount() {
        return 5;
    }

}

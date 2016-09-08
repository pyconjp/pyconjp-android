package jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.ConductFragment;
import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.EventOutlineFragment;
import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.StaffListFragment;
import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.ThemeFragment;
import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment.WhatIsFragment;

/**
 * Created by wj on 16/8/3.
 */

public class AboutPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

    public AboutPagerAdapter(FragmentManager fm) {
        super(fm);
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
                return "PyCon JPってなに？";
            case 1:
                return "スタッフ一覧";
            case 2:
                return "行動規範";
            case 3:
                return "開催概要";
            case 4:
                return "今年のテーマについて";
        }
        return "";
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public int getCount() {
        return 5;
    }

}

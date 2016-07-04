package jp.pycon.pyconjp2016app.Feature.Talks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.pycon.pyconjp2016app.Feature.Talks.Adapter.TalkListAdapter;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 4/22/16.
 */
public class MyTalksFragment extends Fragment implements ViewPager.OnPageChangeListener {

    public static MyTalksFragment newInstance() {
        MyTalksFragment fragment = new MyTalksFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_pager, container, false);
        TabLayout tab= (TabLayout)v.findViewById(R.id.tab_layout);
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPager pager = (ViewPager)v.findViewById(R.id.view_pager);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                MyTalkListFragment f = MyTalkListFragment.newInstance(position);
                return f;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.conference_day_1);
                    case 1:
                        return getString(R.string.conference_day_2);
                }
                return "";
            }
        };
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
        tab.setupWithViewPager(pager);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
}
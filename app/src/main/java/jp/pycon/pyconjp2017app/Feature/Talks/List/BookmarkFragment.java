package jp.pycon.pyconjp2017app.Feature.Talks.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import jp.pycon.pyconjp2017app.Model.Realm.RealmDaysObject;
import jp.pycon.pyconjp2017app.Model.Realm.RealmStringObject;
import jp.pycon.pyconjp2017app.R;

/**
 * Created by rhoboro on 4/22/16.
 */
public class BookmarkFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private Realm realm;
    private View view;
    private Context context;
    public static BookmarkFragment newInstance() {
        BookmarkFragment fragment = new BookmarkFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        setHasOptionsMenu(true);
        realm = Realm.getDefaultInstance();
        TabLayout tab= (TabLayout)view.findViewById(R.id.tab_layout);
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPager pager = (ViewPager)view.findViewById(R.id.view_pager);

        final RealmDaysObject days = realm.where(RealmDaysObject.class).findFirst();
        final RealmList<RealmStringObject> list = days.getDays();
        final RealmResults<RealmStringObject> results = list.sort("string");

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                TalkListFragment f = TalkListFragment.newInstance(position, true);
                return f;
            }

            @Override
            public int getCount() {
                return results.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return results.get(position).getString();
            }
        };
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
        tab.setupWithViewPager(pager);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewPager pager = (ViewPager)view.findViewById(R.id.view_pager);
        pager.setAdapter(null);
        realm.close();
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
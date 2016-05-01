package jp.pycon.pyconjp2016app.Feature.Talks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 4/22/16.
 */
public class MyTalksFragment extends Fragment {

    public static MyTalksFragment newInstance() {
        MyTalksFragment fragment = new MyTalksFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_talks, container, false);
        return v;
    }
}
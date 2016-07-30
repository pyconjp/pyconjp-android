package jp.pycon.pyconjp2016app.Feature.Schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/30/16.
 */
public class ScheduleFragment extends Fragment {
    public ScheduleFragment() {

    }

    public static ScheduleFragment newInstance() {
        ScheduleFragment f = new ScheduleFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        return v;
    }
}

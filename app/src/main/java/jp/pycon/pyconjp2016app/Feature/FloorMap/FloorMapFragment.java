package jp.pycon.pyconjp2016app.Feature.FloorMap;

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
public class FloorMapFragment extends Fragment{
    public FloorMapFragment() {

    }
    public static FloorMapFragment newInstance() {
        FloorMapFragment f = new FloorMapFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_floor_map, container, false);
        return v;
    }
}
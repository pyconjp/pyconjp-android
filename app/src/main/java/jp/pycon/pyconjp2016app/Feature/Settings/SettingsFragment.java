package jp.pycon.pyconjp2016app.Feature.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/24/16.
 */
public class SettingsFragment extends Fragment {
    public SettingsFragment() {

    }

    public static SettingsFragment newInstance() {
        SettingsFragment f = new SettingsFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        return v;
    }
}

package jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.pycon.pyconjp2016app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemeFragment extends Fragment {

    public ThemeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_theme, container, false);
    }

}

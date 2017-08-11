package jp.pycon.pyconjp2017app.Feature.About.AboutOrganizer.SubFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.pycon.pyconjp2017app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WhatIsFragment extends Fragment {

    public WhatIsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_what_is, container, false);
    }

}

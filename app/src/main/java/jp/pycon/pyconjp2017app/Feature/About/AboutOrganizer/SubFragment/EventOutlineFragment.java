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
public class EventOutlineFragment extends Fragment {

    public EventOutlineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_outline, container, false);
    }

}

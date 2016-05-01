package jp.pycon.pyconjp2016app.Feature.About;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 4/22/16.
 */
public class AboutFragment extends Fragment {

    private Context mContext;

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        ListView listView = (ListView) v.findViewById(R.id.about_list_view);

        String[] aboutList = {getString(R.string.about_organizers), getString(R.string.about_this_app), getString(R.string.about_feedback)};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, R.layout.cell_about, aboutList);
        listView.setAdapter(arrayAdapter);
        return v;
    }
}

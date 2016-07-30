package jp.pycon.pyconjp2016app.Feature.FloorMap;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/30/16.
 */
public class FloorMapFragment extends Fragment{

    private String[] FLOORS = {"1階", "2階", "3階"};
    private Context context;
    public FloorMapFragment() {

    }
    public static FloorMapFragment newInstance() {
        FloorMapFragment f = new FloorMapFragment();
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_floor_map, container, false);

        ListView listView = (ListView)v.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FloorMapViewAcitivity.start(context);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.cell_floor_map, FLOORS);
        listView.setAdapter(adapter);
        return v;
    }
}
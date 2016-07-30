package jp.pycon.pyconjp2016app.Feature.FloorMap;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/30/16.
 */
public class FloorMapFragment extends Fragment{

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

        final List<Floor> floors = new ArrayList<>();
        floors.add(Floor.F1);
        floors.add(Floor.F2);
        floors.add(Floor.F3);
        ListView listView = (ListView)v.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Floor floor = floors.get(i);
                FloorMapViewAcitivity.start(context, floor.getTitleResId(), floor.getImageResId());
            }
        });
        FloorAdapter adapter = new FloorAdapter(getContext(), R.layout.cell_floor_map, floors);
        listView.setAdapter(adapter);
        return v;
    }

    public static class FloorAdapter extends ArrayAdapter<Floor> {

        private LayoutInflater inflater;
        private Context context;
        public FloorAdapter(Context context,
                            int textViewResourceId,
                            List<Floor> list) {
            super(context, textViewResourceId, list);
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Floor floor = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.cell_floor_map, null);
            }
            ImageView imageView = (ImageView)convertView.findViewById(R.id.image_view);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), floor.getThumResId(), null));
            TextView textView = (TextView)convertView.findViewById(R.id.title_text_view);
            textView.setText(floor.getTitleResId());
            TextView descTextView = (TextView)convertView.findViewById(R.id.description_text_view);
            descTextView.setText(floor.getDescResId());
            return convertView;
        }
    }

    public enum Floor {
        F1(R.drawable.ic_looks_one_black_36dp, R.string.floor_map_01, R.string.floor_map_description_01, R.drawable.floor_map_01),
        F2(R.drawable.ic_looks_two_black_36dp, R.string.floor_map_02, R.string.floor_map_description_02, R.drawable.floor_map_02),
        F3(R.drawable.ic_looks_3_black_36dp, R.string.floor_map_03, R.string.floor_map_description_03, R.drawable.floor_map_03);
        private final int thumResId;
        private final int imageResId;
        private final int descResId;
        private final int titleResId;
        Floor(int thumResId, int titleResId, int descResId, int imageResId) {
            this.thumResId = thumResId;
            this.titleResId = titleResId;
            this.descResId = descResId;
            this.imageResId = imageResId;
        }

        public int getThumResId() {
            return thumResId;
        }

        public int getTitleResId() {
            return titleResId;
        }

        public int getDescResId() {
            return descResId;
        }

        public int getImageResId() {
            return imageResId;
        }
    }
}
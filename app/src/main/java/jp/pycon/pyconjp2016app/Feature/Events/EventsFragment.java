package jp.pycon.pyconjp2016app.Feature.Events;

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
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/30/16.
 */
public class EventsFragment extends Fragment{

    private Context context;
    public EventsFragment() {

    }
    public static EventsFragment newInstance() {
        EventsFragment f = new EventsFragment();
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

        final List<Event> events = Arrays.asList(Event.values());
        ListView listView = (ListView)v.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Event event = events.get(i);
                EventsDetailActivity.start(context, event.getTitleResId(), event.getUrlResId());
            }
        });
        EventsAdapter adapter = new EventsAdapter(getContext(), R.layout.cell_floor_map, events);
        listView.setAdapter(adapter);
        return v;
    }

    public static class EventsAdapter extends ArrayAdapter<Event> {

        private LayoutInflater inflater;
        private Context context;
        public EventsAdapter(Context context,
                            int textViewResourceId,
                            List<Event> list) {
            super(context, textViewResourceId, list);
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Event floor = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.cell_events, null);
            }
            TextView textView = (TextView)convertView.findViewById(R.id.title_text_view);
            textView.setText(floor.getTitleResId());
            TextView descTextView = (TextView)convertView.findViewById(R.id.description_text_view);
            descTextView.setText(floor.getDescResId());
            return convertView;
        }
    }

    public enum Event {
        INVITED_TALK(R.string.invited_talk, R.string.description_invited_talk, R.string.url_invited_talk),
        JOB_FAIR(R.string.job_fair, R.string.description_job_fair, R.string.url_job_fair),
        PRODUCTS_FIAR(R.string.products_fair, R.string.description_products_fair, R.string.url_products_fair),
        TUTORIAL(R.string.tutorial, R.string.description_tutorial, R.string.url_tutorial),
        SPRINT(R.string.sprint, R.string.description_sprint, R.string.url_sprint);
        private final int descResId;
        private final int titleResId;
        private final int urlResId;
        Event(int titleResId, int descResId, int urlResId) {
            this.titleResId = titleResId;
            this.descResId = descResId;
            this.urlResId = urlResId;
        }

        public int getTitleResId() {
            return titleResId;
        }

        public int getDescResId() {
            return descResId;
        }

        public int getUrlResId() {
            return urlResId;
        }
    }
}
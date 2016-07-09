package jp.pycon.pyconjp2016app.Feature.Talks.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Feature.Talks.RealmScheduleObject;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/8/16.
 */
public class RealmScheduleAdapter extends RealmRecyclerViewAdapter<RealmScheduleObject, RealmScheduleAdapter.MyViewHolder> {

    private final Context context;

    public RealmScheduleAdapter(Context context, RealmResults<RealmScheduleObject> data) {
        super(context, data, true);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MyViewHolder holder = new MyViewHolder(inflater.inflate(MyViewHolder.LAYOUT_ID, parent, false));
        TypedValue val = new TypedValue();
        if (context.getTheme() != null) {
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, val, true);
        }
        holder.itemView.setBackgroundResource(val.resourceId);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 詳細画面を表示
                Toast.makeText(view.getContext(), holder.title.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RealmScheduleObject obj = getData().get(position);
        holder.obj = obj;
        holder.title.setText(obj.title);
        holder.speaker.setText(obj.speaker);
        holder.time.setText(obj.time);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        static final int LAYOUT_ID = R.layout.cell_schedule;
        public RealmScheduleObject obj;

        final TextView speaker;
        final TextView time;
        final TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            speaker = (TextView)itemView.findViewById(R.id.speaker);
            time = (TextView)itemView.findViewById(R.id.time);
            title = (TextView)itemView.findViewById(R.id.title);
        }
    }
}

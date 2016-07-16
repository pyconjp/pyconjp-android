package jp.pycon.pyconjp2016app.Feature.Talks.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/8/16.
 */
public class RealmScheduleAdapter extends RealmRecyclerViewAdapter<RealmPresentationObject, RealmScheduleAdapter.MyViewHolder> {

    public interface RealmScheduleAdapterListener {
        void onClick(int pk);
    }

    private RealmScheduleAdapterListener mListener;

    private final Context context;

    public RealmScheduleAdapter(Context context, RealmResults<RealmPresentationObject> data) {
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
                if (mListener != null) {
                    mListener.onClick(holder.obj.pk);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RealmPresentationObject obj = getData().get(position);
        holder.obj = obj;
        holder.title.setText(obj.title);
        holder.speaker.setText(obj.speakerString());
        holder.time.setText(obj.time);
    }

    public void setOnClickListener(RealmScheduleAdapterListener listener) {
        this.mListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        static final int LAYOUT_ID = R.layout.cell_schedule;
        public RealmPresentationObject obj;

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

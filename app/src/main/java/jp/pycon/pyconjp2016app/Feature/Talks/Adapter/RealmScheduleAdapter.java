package jp.pycon.pyconjp2016app.Feature.Talks.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.Util.ColorUtil;

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
        holder.start.setText(obj.dispStart);
        int bookmarkVisibility = obj.bookmark ? View.VISIBLE : View.INVISIBLE;
        holder.bookmark.setVisibility(bookmarkVisibility);
        final String room = obj.rooms;
        if (!TextUtils.isEmpty(room)) {
            holder.room.setVisibility(View.VISIBLE);
            holder.room.setText(obj.rooms);
            holder.room.setTextColor(ContextCompat.getColor(context, ColorUtil.getRoomColor(obj.rooms)));
        }
        if (TextUtils.equals(obj.language, "en")) {
            holder.en.setVisibility(View.VISIBLE);
            holder.ja.setVisibility(View.GONE);
        } else if (TextUtils.equals(obj.language, "ja")) {
            holder.en.setVisibility(View.GONE);
            holder.ja.setVisibility(View.VISIBLE);
        }
    }

    public void setOnClickListener(RealmScheduleAdapterListener listener) {
        this.mListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        static final int LAYOUT_ID = R.layout.cell_schedule;
        public RealmPresentationObject obj;

        final TextView speaker;
        final TextView start;
        final TextView title;
        final TextView room;
        final TextView en;
        final TextView ja;
        final ImageView bookmark;

        public MyViewHolder(View itemView) {
            super(itemView);
            speaker = (TextView)itemView.findViewById(R.id.speaker);
            start = (TextView)itemView.findViewById(R.id.start);
            title = (TextView)itemView.findViewById(R.id.title);
            room = (TextView)itemView.findViewById(R.id.room);
            bookmark = (ImageView)itemView.findViewById(R.id.bookmark);
            en = (TextView)itemView.findViewById(R.id.language_en);
            ja = (TextView)itemView.findViewById(R.id.language_ja);
        }
    }
}

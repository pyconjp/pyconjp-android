package jp.pycon.pyconjp2016app.Feature.Talks.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.BR;


/**
 * Created by rhoboro on 4/23/16.
 */
public class TalkListAdapter extends RecyclerView.Adapter<TalkListAdapter.TalkBindingHolder> {

    Context mContext;
    List<RealmPresentationObject> talks;

    static class TalkBindingHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        public TalkBindingHolder(View item) {
            super(item);
            binding = DataBindingUtil.bind(item);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public TalkListAdapter(Context context, ArrayList<RealmPresentationObject> talks) {
        this.mContext = context;
        this.talks = talks;
    }

    @Override
    public TalkBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_talk, parent, false);
        return new TalkBindingHolder(v);
    }

    @Override
    public void onBindViewHolder(TalkBindingHolder holder, int position) {
        holder.getBinding().setVariable(BR.realmScheduleObject, talks.get(position));
        View v =holder.getBinding().getRoot();

        // TODO: デザイン確認
//        TextView textView = (TextView) v.findViewById(R.id.card_room);
//        textView.setTextColor(ContextCompat.getColor(mContext, getColor(position)));
        ImageView star = (ImageView) v.findViewById(R.id.card_star);
        star.setImageDrawable(getStar(position));
    }

    private int getColor(int position) {
        // TODO: デザイン確認用
        switch (position%4) {
            case 0:
                return R.color.colorMagenta;
            case 1:
                return R.color.colorOrange;
            case 2:
                return R.color.colorYellowGreen;
            case 3:
                return R.color.colorPrimary;
        }
        return R.color.colorBlack;
    }

    private Drawable getStar(int position) {
        // TODO: デザイン確認用
        Drawable star;
        if (position%2 == 0) {
            star = ContextCompat.getDrawable(mContext, R.drawable.ic_star_black_36dp);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                star.setTint(ContextCompat.getColor(mContext, R.color.colorOrange));
            }
        } else {
            star = ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_black_36dp);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                star.setTint(ContextCompat.getColor(mContext, R.color.colorGray));
            }
        }
        return star;
    }

    @Override
    public int getItemCount() {
        return talks.size();
    }

    public void updateTalks(RealmResults<RealmPresentationObject> talkList) {
        talks.clear();
        talks.addAll(talkList);
        notifyDataSetChanged();
    }
}

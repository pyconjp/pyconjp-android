package jp.pycon.pyconjp2016app.Feature.Talks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 4/23/16.
 */
public class TalkListFragment extends Fragment {


    public TalkListFragment() {

    }

    public static TalkListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        TalkListFragment fragment = new TalkListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_talk_list, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.talk_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        final int dividerHeight = (int)(1 * getResources().getDisplayMetrics().density);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);

                final RecyclerView.LayoutManager manager = parent.getLayoutManager();

                final int left = parent.getPaddingLeft();
                final int right = parent.getWidth() - parent.getPaddingRight();

                final int childCount = parent.getChildCount();
                for (int i = 1; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    if (params.getViewLayoutPosition() == 0) {
                        continue;
                    }
                    final int top = manager.getDecoratedTop(child) - params.topMargin + Math.round(ViewCompat.getTranslationY(child));
                    final int bottom = top + dividerHeight;
                    c.drawRect(left, top, right, bottom, paint);
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
                int top = position == 0 ? 0 : dividerHeight;
                outRect.set(0, top, 0, 0);
            }
        });

        List<TalkScheduleModel> data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            TalkScheduleModel model = new TalkScheduleModel();
            if (i%3 == 0) {
                model.setTime("10:00AM");
                model.setSpeaker("Takesxi Sximada");
                model.setTitle("【初心者向けPythonチュートリアル】Webスクレイピングに挑戦してみよう");
            } else if (i%3 == 1) {
                model.setTime("02:00PM");
                model.setSpeaker("Kimikazu Kato");
                model.setTitle("Pythonを使った機械学習入門");
            } else {
                model.setTime("02:00PM");
                model.setSpeaker("Takahiro Ikeuchi");
                model.setTitle("Python x Edison x AWSではじめる IoT");
            }

            data.add(model);
        }
        recyclerView.setAdapter(new TalkListAdapter(getContext(), data));

        return v;
    }

    private static class TalkScheduleModel {

        private int id;
        private String title;
        private String time;
        private String speaker;

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setSpeaker(String speaker) {
            this.speaker = speaker;
        }

        public String getSpeaker() {
            return speaker;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        static final int LAYOUT_ID = R.layout.cell_schedule;

        final TextView speaker;
        final TextView time;
        final TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            speaker = (TextView)itemView.findViewById(R.id.speaker);
            time = (TextView)itemView.findViewById(R.id.time);
            title = (TextView)itemView.findViewById(R.id.title);
        }
    }

    private static class TalkListAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final LayoutInflater inflater;
        private final List<TalkScheduleModel> data;

        private TalkListAdapter(Context context, List<TalkScheduleModel> data) {
            this.inflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(ViewHolder.LAYOUT_ID, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TalkScheduleModel model = data.get(position);
            holder.title.setText(model.getTitle());
            holder.speaker.setText(model.getSpeaker());
            holder.time.setText(model.getTime());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}

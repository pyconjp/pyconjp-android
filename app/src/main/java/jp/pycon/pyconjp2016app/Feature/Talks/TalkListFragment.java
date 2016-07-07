package jp.pycon.pyconjp2016app.Feature.Talks;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.API.Client.APIClient;
import jp.pycon.pyconjp2016app.API.Entity.ContentEntity;
import jp.pycon.pyconjp2016app.API.Entity.PyConJPScheduleEntity;
import jp.pycon.pyconjp2016app.Feature.Talks.Adapter.RealmScheduleAdapter;
import jp.pycon.pyconjp2016app.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rhoboro on 4/23/16.
 */
public class TalkListFragment extends Fragment {


    private Realm realm;
    private RealmResults<RealmScheduleObject> scheduleObjects;
    private RecyclerView recyclerView;
    RealmScheduleAdapter adapter;

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
        recyclerView = (RecyclerView) v.findViewById(R.id.talk_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        final int dividerHeight = (int) (1 * getResources().getDisplayMetrics().density);

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
                int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
                int top = position == 0 ? 0 : dividerHeight;
                outRect.set(0, top, 0, 0);
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();

        scheduleObjects = realm.where(RealmScheduleObject.class).findAll();
        adapter = new RealmScheduleAdapter(getContext(), scheduleObjects);
        recyclerView.setAdapter(adapter);
        getPyConJPSchedule(recyclerView);
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    private void getPyConJPSchedule(final RecyclerView recyclerView) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pycon.jp/2016/site_media/static/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIClient apiClient = retrofit.create(APIClient.class);
        rx.Observable<PyConJPScheduleEntity> observable =  apiClient.getPyConJPSchedule();
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PyConJPScheduleEntity>() {
                               @Override
                               public void onCompleted() {
                                   scheduleObjects = realm.where(RealmScheduleObject.class).findAllAsync();
                                   adapter.notifyDataSetChanged();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onNext(PyConJPScheduleEntity pyConJPScheduleEntity) {
                                   Log.d("tag", "data0" + pyConJPScheduleEntity.data0.contents.toString());
                                   Log.d("tag", "data1" + pyConJPScheduleEntity.data1.contents.toString());
                                   Log.d("tag", "data2" + pyConJPScheduleEntity.data2.contents.toString());
                                   Log.d("tag", "data3" + pyConJPScheduleEntity.data3.contents.toString());

                                   // 前回結果を Realm から削除
                                   final RealmResults<RealmScheduleObject> results = realm.where(RealmScheduleObject.class).findAll();
                                   realm.executeTransaction(new Realm.Transaction() {
                                       @Override
                                       public void execute(Realm realm) {
                                           results.deleteAllFromRealm();
                                       }
                                   });
                                   // TODO: 結果を Realm に格納
                                   realm.beginTransaction();
                                   for (ContentEntity entity: pyConJPScheduleEntity.data1.contents) {
                                       RealmScheduleObject obj = realm.createObject(RealmScheduleObject.class);
                                       obj.time = entity.time;
                                       obj.speaker = entity.speaker;
                                       obj.title  = entity.title;
                                   }
                                   realm.commitTransaction();
                               }
                           }
                );
    }
}

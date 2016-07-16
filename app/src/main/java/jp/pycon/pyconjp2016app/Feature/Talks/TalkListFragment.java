package jp.pycon.pyconjp2016app.Feature.Talks;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.API.Client.APIClient;
import jp.pycon.pyconjp2016app.API.Client.LocalResponseInterceptor;
import jp.pycon.pyconjp2016app.BuildConfig;
import jp.pycon.pyconjp2016app.Feature.Talks.Adapter.RealmScheduleAdapter;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationDetailEntity;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationDetailObject;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.Model.Realm.RealmSpeakerObject;
import jp.pycon.pyconjp2016app.R;
import okhttp3.OkHttpClient;
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


    private Context mContext;
    private Realm realm;
    private RecyclerView recyclerView;
    private RealmResults<RealmPresentationObject> schedules;
    private RealmChangeListener realmListener;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
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
        paint.setColor(Color.argb(15, 0, 0, 0));
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        realm = Realm.getDefaultInstance();

        schedules = realm.where(RealmPresentationObject.class).findAll();
        adapter = new RealmScheduleAdapter(getContext(), schedules);
        adapter.setOnClickListener(new RealmScheduleAdapter.RealmScheduleAdapterListener() {
            @Override
            public void onClick(int pk) {
                RealmResults<RealmPresentationDetailObject> results = realm.where(RealmPresentationDetailObject.class)
                        .equalTo("pk", pk)
                        .findAll();
                if (results.size() != 0) {
                    final Intent intent = new Intent(mContext, TalkDetailActivity.class);
                    intent.putExtra(TalkDetailActivity.BUNDLE_KEY_PRESENTATION_ID, pk);
                    startActivity(intent);
                } else {
                    getPyConJPPresentation(pk);
                    Toast.makeText(getContext(), ""+pk,Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
        realmListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                adapter.notifyDataSetChanged();
            }
        };
        schedules.addChangeListener(realmListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        schedules.removeChangeListener(realmListener);
        realm.close();
    }

    private void getPyConJPPresentation(final int pk) {
        APIClient apiClient = getClient(BuildConfig.PRODUCTION);
        rx.Observable<PresentationDetailEntity> observable = apiClient.getPyConJPPresentationDetail(pk);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PresentationDetailEntity>() {
                               @Override
                               public void onCompleted() {
                                   final Intent intent = new Intent(mContext, TalkDetailActivity.class);
                                   intent.putExtra(TalkDetailActivity.BUNDLE_KEY_PRESENTATION_ID, pk);
                                   startActivity(intent);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   Toast.makeText(mContext, "error" + e, Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onNext(final PresentationDetailEntity presentation) {
                                   saveEntity(pk, presentation);
                               }
                           }
                );
    }

    private void saveEntity(final int pk, final PresentationDetailEntity entity) {
        RealmResults<RealmPresentationObject> results = realm.where(RealmPresentationObject.class)
                .equalTo("pk", pk)
                .findAll();
        realm.beginTransaction();
        RealmPresentationDetailObject obj = realm.createObject(RealmPresentationDetailObject.class);
        obj.title = results.get(0).title;
        obj.pk = pk;
        obj.description = entity.description;
        obj.abst = entity.abst;
        RealmList<RealmSpeakerObject> speakers = new RealmList<>();
        for (String speaker : entity.speakers) {
            RealmSpeakerObject speakerObject = realm.createObject(RealmSpeakerObject.class);
            speakerObject.speaker = speaker;
            speakers.add(speakerObject);
        }
        obj.speakers = speakers;
        realm.commitTransaction();
    }

    private APIClient getClient(boolean production) {
        Retrofit retrofit;
        if (production) {
            // 本番APIを叩く
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        } else {
            // ローカルのサンプルファイルを利用する
            LocalResponseInterceptor i = new LocalResponseInterceptor(mContext);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(i)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit.create(APIClient.class);
    }
}

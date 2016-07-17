package jp.pycon.pyconjp2016app.Feature.Talks.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Feature.Talks.Adapter.TalkListAdapter;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 4/23/16.
 */
public class MyTalkListFragment extends Fragment {

    private TalkListAdapter mTalkAdapter;
    private Realm realm;

    public MyTalkListFragment() {

    }

    public static MyTalkListFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        MyTalkListFragment fragment = new MyTalkListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_talk_list, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.talk_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mTalkAdapter = new TalkListAdapter(getContext(), new ArrayList<RealmPresentationObject>());
        int position = getArguments().getInt("position", 0);
        recyclerView.setAdapter(mTalkAdapter);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        fetchTalkList(0);
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    public void fetchTalkList(int position) {
        RealmResults<RealmPresentationObject> talks = realm.where(RealmPresentationObject.class).findAll();
        mTalkAdapter.updateTalks(talks);
    }
}

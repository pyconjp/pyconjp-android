package jp.pycon.pyconjp2016app.Feature.Talks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jp.pycon.pyconjp2016app.API.Entity.Talk;
import jp.pycon.pyconjp2016app.Feature.Talks.Adapter.TalkListAdapter;
import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 4/23/16.
 */
public class TalkListFragment extends Fragment {

    private TalkListAdapter mTalkAdapter;

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
        mTalkAdapter = new TalkListAdapter(getContext(), new ArrayList<Talk>());
        int position = getArguments().getInt("position", 0);
        recyclerView.setAdapter(mTalkAdapter);
        fetchTalkList(position);
        return v;
    }

    public void fetchTalkList(int position) {
        Talk talk = new Talk();
        Talk talk1 = new Talk();
        List<Talk> list = new ArrayList<>();
        list.add(talk);
        list.add(talk1);
        list.add(talk1);
        list.add(talk1);
        list.add(talk1);
        list.add(talk1);
        list.add(talk1);
        list.add(talk1);
        mTalkAdapter.updateTalks(list);
    }
}

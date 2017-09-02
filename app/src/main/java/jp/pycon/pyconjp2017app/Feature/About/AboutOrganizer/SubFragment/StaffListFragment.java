package jp.pycon.pyconjp2017app.Feature.About.AboutOrganizer.SubFragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.pycon.pyconjp2017app.API.Client.GHPagesAPIClient;
import jp.pycon.pyconjp2017app.App;
import jp.pycon.pyconjp2017app.Feature.About.AboutOrganizer.Adapter.StaffListAdapter;
import jp.pycon.pyconjp2017app.Model.GHPages.StaffEntity;
import jp.pycon.pyconjp2017app.Model.GHPages.StaffListEntity;
import jp.pycon.pyconjp2017app.R;
import jp.pycon.pyconjp2017app.databinding.FragmentStaffListBinding;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffListFragment extends android.support.v4.app.Fragment{

    private FragmentStaffListBinding binding;
    private StaffListAdapter adapter;
    private List<StaffEntity> list = new ArrayList<>();
    private Context context;

    public StaffListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff_list, container, false);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StaffListAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getStaffList();
    }

    private void getStaffList() {
        GHPagesAPIClient client = ((App)getActivity().getApplication()).getGHPagesAPIClient();
        Observable<StaffListEntity> observable = client.getStaffList();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StaffListEntity>() {
                    @Override
                    public void onCompleted() {
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(StaffListEntity staffListEntity) {
                        list.clear();
                        for (StaffEntity entity: staffListEntity.staffList) {
                            list.add(entity);
                        }
                    }
                });
    }
}

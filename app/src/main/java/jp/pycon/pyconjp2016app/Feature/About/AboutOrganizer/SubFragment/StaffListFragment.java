package jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.SubFragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.Adapter.StaffListAdapter;
import jp.pycon.pyconjp2016app.Model.PyConJP.StaffEntity;
import jp.pycon.pyconjp2016app.Model.PyConJP.StaffListEntity;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.databinding.FragmentStaffListBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffListFragment extends android.support.v4.app.Fragment{

    private FragmentStaffListBinding binding;
    private List<StaffEntity> list = new ArrayList<>();

    public StaffListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        InputStream inputStream = getResources().openRawResource(R.raw._2016_ja_api_staff_list_sample);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String jsonString = writer.toString();

        Gson gson = new Gson();
        StaffListEntity staffListEntity = gson.fromJson(jsonString, StaffListEntity.class);
        list = staffListEntity.getStaffList();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_staff_list, container, false);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new StaffListAdapter(getActivity(), list));

        View view = binding.getRoot();
        return view;
    }

}

package jp.pycon.pyconjp2016app.Feature.Keynote;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.pycon.pyconjp2016app.API.Client.GHPagesAPIClient;
import jp.pycon.pyconjp2016app.App;
import jp.pycon.pyconjp2016app.Model.GHPages.KeynoteEntity;
import jp.pycon.pyconjp2016app.Model.GHPages.KeynoteListEntity;
import jp.pycon.pyconjp2016app.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rhoboro on 8/28/16.
 */
public class KeynoteFragment extends Fragment {
    private Context context;
    private ListView listView;


    public static KeynoteFragment newInstance() {
        KeynoteFragment f = new KeynoteFragment();
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_keynote, container, false);
        listView = (ListView)v.findViewById(R.id.list_view);
        getKeynotes();
        return v;
    }

    private void getKeynotes() {
        GHPagesAPIClient client = ((App)getActivity().getApplication()).getGHPagesAPIClient();
        Observable<KeynoteListEntity> observable = client.getKeynotes();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<KeynoteListEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(KeynoteListEntity keynoteListEntity) {
                        List<KeynoteEntity> list = new ArrayList<>();
                        for (KeynoteEntity entity: keynoteListEntity.keynotes) {
                            list.add(entity);
                        }
                        showKeynotes(list);
                    }
                });
    }

    private void showKeynotes(List<KeynoteEntity> list) {
        KeynoteAdapter adapter = new KeynoteAdapter(context, R.layout.cell_keynote, list);
        listView.setAdapter(adapter);
    }
    public static class KeynoteAdapter extends ArrayAdapter<KeynoteEntity> {

        private LayoutInflater inflater;
        private Context context;

        public KeynoteAdapter(Context context,
                              int textViewResourceId,
                              List<KeynoteEntity> list) {
            super(context, textViewResourceId, list);
            this.context = context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            KeynoteEntity entity = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.cell_keynote, null);
            }
            TextView speaker = (TextView)convertView.findViewById(R.id.keynote_speaker);
            speaker.setText(entity.speaker);
            ImageView image = (ImageView)convertView.findViewById(R.id.keynote_image);
            Picasso.with(context).load(entity.imageUri).into(image);
            TextView detail = (TextView)convertView.findViewById(R.id.keynote_detail);
            detail.setText(entity.detail);
            return convertView;
        }
    }
}

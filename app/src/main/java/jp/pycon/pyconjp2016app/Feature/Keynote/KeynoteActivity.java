package jp.pycon.pyconjp2016app.Feature.Keynote;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
 * Created by rhoboro on 8/2/16.
 */
public class KeynoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keynote);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        getKeynotes();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, KeynoteActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getKeynotes() {
        GHPagesAPIClient client = ((App)getApplication()).getGHPagesAPIClient();
        Observable<KeynoteListEntity> observable = client.getKeynotes();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<KeynoteListEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

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
        ListView listView = (ListView)findViewById(R.id.list_view);
        KeynoteAdapter adapter = new KeynoteAdapter(getApplicationContext(), R.layout.cell_keynote, list);
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

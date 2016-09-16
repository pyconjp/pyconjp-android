package jp.pycon.pyconjp2016app.Feature.About;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jp.pycon.pyconjp2016app.Feature.About.AboutOrganizer.AboutOrganizerActivity;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.Util.FirebaseUtil;

/**
 * Created by rhoboro on 4/22/16.
 */
public class AboutFragment extends Fragment  implements AdapterView.OnItemClickListener {

    private Context mContext;

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
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
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        ListView listView = (ListView) v.findViewById(R.id.about_list_view);
        listView.setOnItemClickListener(this);

        String[] aboutList = {getString(R.string.about_organizers), getString(R.string.about_sponsors), getString(R.string.about_this_app), getString(R.string.about_feedback)};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, R.layout.cell_about, aboutList);
        listView.setAdapter(arrayAdapter);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(mContext, AboutOrganizerActivity.class);
                break;
            case 1:
                intent.setClass(mContext, AboutSponsorActivity.class);
                FirebaseUtil.sendAbout(mContext, getString(R.string.about_sponsors));
                break;
            case 2:
                intent.setClass(mContext, AboutAppActivity.class);
                FirebaseUtil.sendAbout(mContext, getString(R.string.about_this_app));
                break;
            case 3:
                intent = generateFeedbackIntent();
                FirebaseUtil.sendAbout(mContext, getString(R.string.about_feedback));
                break;
        }
        startActivity(intent);
    }

    private Intent generateFeedbackIntent() {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"symposion@pycon.jp"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for PyCon JP 2016 App");
        intent.putExtra(Intent.EXTRA_TEXT, "Android OS:\nDevice:\nReply-To:\n\nFeedback:\n");
        return intent;
    }
}

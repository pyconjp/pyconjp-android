package jp.pycon.pyconjp2016app.Feature.Talks.Detail;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationEntity;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.Util.PreferencesManager;

/**
 * Created by rhoboro on 7/17/16.
 */
public class BookmarkDialog extends DialogFragment {

    public static final String BUNDLE_KEY_PRESENTATION_ID = "bundle_key_presentation_id";
    private Context mContext;
    private Realm realm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        realm.close();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        final int pk = bundle.getInt(BUNDLE_KEY_PRESENTATION_ID);

        final boolean[] checked = {true};
        final CharSequence[] notification = {getString(R.string.dialog_list_notification)};
        final Dialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.dialog_title_bookmark_on)
                .setMultiChoiceItems(notification, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean flag) {
                        checked[which] = flag;
                    }
                })
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PreferencesManager.putBookmark(mContext, pk);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null)
                .create();
        return dialog;
    }
}

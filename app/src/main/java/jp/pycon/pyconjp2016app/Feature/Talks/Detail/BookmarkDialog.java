package jp.pycon.pyconjp2016app.Feature.Talks.Detail;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import io.realm.Realm;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.R;
import jp.pycon.pyconjp2016app.Util.NotificationUtil;
import jp.pycon.pyconjp2016app.Util.PreferencesManager;

/**
 * Created by rhoboro on 7/17/16.
 */
public class BookmarkDialog extends DialogFragment {

    public interface BookmarkDialogListener {
        void bookmarkStatusChanged(int pk, boolean added);
    }

    public static final String BUNDLE_KEY_PRESENTATION_ID = "bundle_key_presentation_id";
    private Context mContext;
    private BookmarkDialogListener mListener;
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
        Dialog dialog;
        if (PreferencesManager.isBookmarkContains(mContext, pk)) {
            dialog = new AlertDialog.Builder(mContext)
                    .setTitle(R.string.dialog_title_bookmark_off)
                    .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            PreferencesManager.deleteBookmark(mContext, pk);
                            final RealmPresentationObject obj = realm.where(RealmPresentationObject.class)
                                    .equalTo("pk", pk)
                                    .findFirst();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    obj.bookmark = false;
                                    obj.alert = false;
                                }
                            });
                            NotificationUtil.cancelNotification(mContext, pk);
                            if (mListener != null) {
                                mListener.bookmarkStatusChanged(pk, false);
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, null)
                    .create();
        } else {
            final boolean[] checked = {true};
            final CharSequence[] notification = {getString(R.string.dialog_list_notification)};
            dialog = new AlertDialog.Builder(mContext)
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
                            final RealmPresentationObject obj = realm.where(RealmPresentationObject.class)
                                    .equalTo("pk", pk)
                                    .findFirst();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    obj.bookmark = true;
                                    obj.alert = checked[0];
                                }
                            });
                            if (checked[0]) {
                                NotificationUtil.setNotification(mContext, obj.title, pk, 10);
                                if (mListener != null) {
                                    mListener.bookmarkStatusChanged(pk, true);
                                }
                            }
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, null)
                    .create();
        }
        return dialog;
    }

    public void setmListener(BookmarkDialogListener mListener) {
        this.mListener = mListener;
    }
}

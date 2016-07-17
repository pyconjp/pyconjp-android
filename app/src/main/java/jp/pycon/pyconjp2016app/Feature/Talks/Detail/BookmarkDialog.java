package jp.pycon.pyconjp2016app.Feature.Talks.Detail;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 7/17/16.
 */
public class BookmarkDialog extends DialogFragment {
    private Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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

                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null)
                .create();
        return dialog;
    }
}

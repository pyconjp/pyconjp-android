package jp.pycon.pyconjp2016app.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhoboro on 7/17/16.
 */
public class PreferencesManager {

    private static final String PREFERENCES_NAME = "jp.pycon.pychonjp2016app";
    private static final String PREFERENCES_KEY_BOOKMARK_ARRAY = "preferences_key_bookmark_array";

    public static boolean putBookmark(Context context, int pk) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String bookmark = pref.getString(PREFERENCES_KEY_BOOKMARK_ARRAY, "");
        JSONArray array = new JSONArray();
        if (TextUtils.isEmpty(bookmark)) {
            array.put(String.valueOf(pk));
        } else {
            try {
                array = new JSONArray(bookmark);
                array.put(String.valueOf(pk));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREFERENCES_KEY_BOOKMARK_ARRAY, array.toString());
        return editor.commit();
    }

    public static List<Integer> getBookmark(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String bookmark = pref.getString(PREFERENCES_KEY_BOOKMARK_ARRAY, "");

        List<Integer> list = new ArrayList<>();
        if (!TextUtils.isEmpty(bookmark)) {
            try {
                JSONArray array = new JSONArray(bookmark);
                for (int i = 0; i < array.length(); i++) {
                    list.add(array.optInt(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static boolean deleteBookmark(Context context, int pk) {
        if (isBookmarkContains(context, pk)) {
            SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            List<Integer> list = getBookmark(context);
            while (list.remove((Integer)pk));
            SharedPreferences.Editor editor =  pref.edit();
            JSONArray array = new JSONArray(list);
            editor.putString(PREFERENCES_KEY_BOOKMARK_ARRAY, array.toString());
            return editor.commit();
        }
        return true;
    }

    public static boolean isBookmarkContains(Context context, int pk) {
        List<Integer> list = getBookmark(context);
        return list.contains(pk);
    }
}


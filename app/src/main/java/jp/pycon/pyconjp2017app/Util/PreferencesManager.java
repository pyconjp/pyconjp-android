package jp.pycon.pyconjp2017app.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rhoboro on 7/17/16.
 *
 * SharedPreferencesへのアクセスを行うクラス
 */
public class PreferencesManager {

    private static final String PREFERENCES_NAME = "jp.pycon.pychonjp2017app";
    private static final String PREFERENCES_KEY_BOOKMARK_ARRAY = "preferences_key_bookmark_array";
    private static final String PREFERENCES_KEY_MINUTES_BEFORE = "preferences_key_minutes_before";

    /**
     * 指定されたトークをブックマークに保存します
     * @param context コンテキスト
     * @param pk トークのPK
     * @return 書き込みの成否
     */
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

    /**
     * ブックマークに保存されたトークのPK一覧を返します
     * @param context コンテキスト
     * @return ブックマークに保存されたトークのPK一覧
     */
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

    /**
     * 指定されたトークをブックマークから削除します
     * @param context コンテキスト
     * @param pk ブックマークから削除するトークのPK
     * @return 削除の成否
     */
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

    /**
     * 指定されたトークがブックマークに保存されているかを返します
     * @param context コンテキスト
     * @param pk トークのPK
     * @return ブックマークに保存されている場合はtrue
     */
    public static boolean isBookmarkContains(Context context, int pk) {
        List<Integer> list = getBookmark(context);
        return list.contains(pk);
    }

    /**
     * 通知を行う時間はトークの何分前かの設定を保存します
     * @param context コンテキスト
     * @return 通知を行う時間はトークの何分前か
     */
    public static int getMinutesBefore(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getInt(PREFERENCES_KEY_MINUTES_BEFORE, Context.MODE_PRIVATE);
    }

    /**
     * 通知を行う時間はトークの何分前かの設定を保存します
     * @param context コンテキスト
     * @param minutes 何分前か
     * @return 保存の成否
     */
    public static boolean putMinuteBefore(Context context, int minutes) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREFERENCES_KEY_MINUTES_BEFORE, minutes);
        return editor.commit();
    }
}


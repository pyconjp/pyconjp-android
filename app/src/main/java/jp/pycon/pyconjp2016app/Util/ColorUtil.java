package jp.pycon.pyconjp2016app.Util;

import android.text.TextUtils;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 8/28/16.
 */
public class ColorUtil {
    public static int getRoomColor(String room) {
        int color = R.color.colorLightGray;
        if (TextUtils.isEmpty(room)) {
            return color;
        }
        if (room.contains("201")) {
            color = R.color.colorRedOrange;
        } else if (room.contains("202")) {
            color = R.color.colorYellowOrange;
        } else if (room.contains("203")) {
            color = R.color.colorYellow;
        } else if (room.contains("204")) {
            color = R.color.colorYellowGreen;
        } else if (room.contains("205")) {
            color = R.color.colorBlueGreen;
        } else if (room.contains("Information gallery")) {
            color = R.color.colorPrimary;
        }
        return color;
    }

    public static int getLogoColorIndex(String room) {
        int index = 0;
        if (TextUtils.isEmpty(room)) {
            return index;
        }
        if (room.contains("201")) {
            index = 1;
        } else if (room.contains("202")) {
            index = 2;
        } else if (room.contains("203")) {
            index = 3;
        } else if (room.contains("204")) {
            index = 4;
        } else if (room.contains("205")) {
            index = 5;
        }
        return index;
    }
}

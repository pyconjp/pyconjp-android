package jp.pycon.pyconjp2016app.Util;

import jp.pycon.pyconjp2016app.R;

/**
 * Created by rhoboro on 8/28/16.
 */
public class ColorUtil {
    public static int getRoomColor(String room) {
        int color = R.color.colorRedOrange;
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
        }
        return color;
    }
}

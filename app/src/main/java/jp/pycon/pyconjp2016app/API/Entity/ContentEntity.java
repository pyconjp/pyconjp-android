package jp.pycon.pyconjp2016app.API.Entity;

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rhoboro on 7/5/16.
 */
public class ContentEntity {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("speaker")
    public String speaker;
    @SerializedName("time")
    public String time;

    @Override
    public String toString() {
        return "id:" + id + " title: " + title + " speaker: " + speaker + " time: " + time + "\n";
    }
}

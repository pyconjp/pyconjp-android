package jp.pycon.pyconjp2016app.Model.PyConJP;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rhoboro on 7/11/16.
 */
public class PresentationDetailEntity {

    @SerializedName("category")
    public String category;
    @SerializedName("speakers")
    public String[] speakers;
    @SerializedName("end")
    public String end;
    @SerializedName("description")
    public String description;
    @SerializedName("title")
    public String title;
    @SerializedName("id")
    public int pk;
    @SerializedName("start")
    public String start;
    @SerializedName("rooms")
    public String rooms;
    @SerializedName("abst")
    public String abst;
    @SerializedName("level")
    public String level;
}

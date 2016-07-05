package jp.pycon.pyconjp2016app.API.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rhoboro on 7/5/16.
 */
public class DataEntity {

    @SerializedName("contents")
    public List<ContentEntity> contents;
    @SerializedName("date")
    public String date;
}

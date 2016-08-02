package jp.pycon.pyconjp2016app.Model.GHPages;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by rhoboro on 8/2/16.
 */
public class KeynoteListEntity {

    @SerializedName("keynotes")
    public List<KeynoteEntity> keynotes;
}

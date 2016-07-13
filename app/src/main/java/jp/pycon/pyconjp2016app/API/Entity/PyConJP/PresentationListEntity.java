package jp.pycon.pyconjp2016app.API.Entity.PyConJP;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rhoboro on 7/11/16.
 */
public class PresentationListEntity {
    @SerializedName("presentations")
    public List<PresentationEntity> presentations;
}

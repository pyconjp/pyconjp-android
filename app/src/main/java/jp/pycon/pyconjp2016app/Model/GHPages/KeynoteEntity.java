package jp.pycon.pyconjp2016app.Model.GHPages;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rhoboro on 8/2/16.
 */
public class KeynoteEntity {

    @SerializedName("speaker")
    public String speaker;
    @SerializedName("detail")
    public String detail;
    @SerializedName("image_uri")
    public String imageUri;

}

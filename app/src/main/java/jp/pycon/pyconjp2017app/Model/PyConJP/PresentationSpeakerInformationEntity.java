package jp.pycon.pyconjp2017app.Model.PyConJP;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rhoboro on 9/11/16.
 */
public class PresentationSpeakerInformationEntity {

    @SerializedName("name")
    public String name;
    @SerializedName("twitter")
    public String twitter;
    @SerializedName("github")
    public String github;
    @SerializedName("facebook")
    public String facebook;
    @SerializedName("image_uri")
    public String imageUri;

}

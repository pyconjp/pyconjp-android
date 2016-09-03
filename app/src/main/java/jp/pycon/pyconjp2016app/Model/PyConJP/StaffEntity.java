package jp.pycon.pyconjp2016app.Model.PyConJP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wj on 16/8/31.
 */

public class StaffEntity {

    @SerializedName("teamID")
    @Expose
    private String teamID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("facebook")
    @Expose
    private String facebook;

    /**
     * @return The teamID
     */
    public String getTeamID() {
        return teamID;
    }

    /**
     * @param teamID The teamID
     */
    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The twitter
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     * @param twitter The twitter
     */
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    /**
     * @return The facebook
     */
    public String getFacebook() {
        return facebook;
    }

    /**
     * @param facebook The facebook
     */
    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

}
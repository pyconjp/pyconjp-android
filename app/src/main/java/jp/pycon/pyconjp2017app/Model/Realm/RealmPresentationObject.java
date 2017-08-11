package jp.pycon.pyconjp2017app.Model.Realm;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rhoboro on 7/7/16.
 */
public class RealmPresentationObject extends RealmObject {

    public static final int TYPE_TALK = 0;
    public static final int TYPE_POSTER = 1;

    @PrimaryKey
    public int pk;
    public String title;
    public RealmList<RealmStringObject> speakers;
    public String rooms;
    public boolean bookmark;
    public boolean alert;
    public String day;
    public String start;
    public String end;
    public String dispStart;
    public String language;
    public int type;

    public int getPk() {
        return pk;
    }

    public String getTitle() {
        return title;
    }

    public RealmList<RealmStringObject> getSpeakers() {
        return speakers;
    }

    public String getRooms() {
        return rooms;
    }
    public String speakerString() {
        List<String> strings = new ArrayList<>();
        for (RealmStringObject speaker : speakers) {
            strings.add(speaker.getString());
        }
        return TextUtils.join("\n", strings);
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public String getDay() {
        return day;
    }
}



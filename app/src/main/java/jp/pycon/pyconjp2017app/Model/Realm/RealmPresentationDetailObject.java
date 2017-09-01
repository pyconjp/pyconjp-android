package jp.pycon.pyconjp2017app.Model.Realm;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by rhoboro on 7/15/16.
 */
public class RealmPresentationDetailObject extends RealmObject {
    public String category;
    public RealmList<RealmStringObject> speakers;
    public String end;
    public String description;
    public String title;
    public int pk;
    public String start;
    public String rooms;
    public String abst;
    public String level;
    public String day;
    public String dispDate;
    public String language;

    public String getCategory() {
        return category;
    }

    public RealmList<RealmStringObject> getSpeakers() {
        return speakers;
    }

    public String getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getPk() {
        return pk;
    }

    public String getStart() {
        return start;
    }

    public String getRooms() {
        return rooms;
    }

    public String getAbst() {
        return abst;
    }

    public String getLevel() {
        return level;
    }
    public String speakerstring() {
        List<String> strings = new ArrayList<>();
        for (RealmStringObject speaker : speakers) {
            strings.add(speaker.getString());
        }
        return TextUtils.join("\n", strings);
    }
    public RealmList<RealmSpeakerInformationObject> speakerInformation;
}

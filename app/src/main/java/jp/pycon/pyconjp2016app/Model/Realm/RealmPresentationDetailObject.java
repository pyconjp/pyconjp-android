package jp.pycon.pyconjp2016app.Model.Realm;

import io.realm.RealmObject;

/**
 * Created by rhoboro on 7/15/16.
 */
public class RealmPresentationDetailObject extends RealmObject {
    public String category;
    public String speaker;
    public String end;
    public String description;
    public String title;
    public String pk;
    public String start;
    public String rooms;
    public String abst;
    public String level;

    public String getCategory() {
        return category;
    }

    public String getSpeaker() {
        return speaker;
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

    public String getPk() {
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
}

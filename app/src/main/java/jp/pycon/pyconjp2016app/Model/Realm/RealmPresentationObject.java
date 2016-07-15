package jp.pycon.pyconjp2016app.Model.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rhoboro on 7/7/16.
 */
public class RealmPresentationObject extends RealmObject {

    public int pk;
    public String title;
    public String time;
    public String speaker;
    public String rooms;

    public int getPk() {
        return pk;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getRooms() {
        return rooms;
    }
}



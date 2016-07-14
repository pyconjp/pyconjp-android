package jp.pycon.pyconjp2016app.Model.Realm;

import io.realm.RealmObject;

/**
 * Created by rhoboro on 7/7/16.
 */
public class RealmPresentationObject extends RealmObject {

    public String title;
    public String time;
    public String speaker;
    public String rooms;

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



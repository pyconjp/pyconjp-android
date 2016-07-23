package jp.pycon.pyconjp2016app.Model.Realm;

import io.realm.RealmObject;

/**
 * Created by rhoboro on 7/16/16.
 */
public class RealmStringObject extends RealmObject {
    public String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}

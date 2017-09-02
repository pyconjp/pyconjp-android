package jp.pycon.pyconjp2017app.Model.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by rhoboro on 7/23/16.
 */
public class RealmDaysObject extends RealmObject {
    private RealmList<RealmStringObject> days;

    public RealmList<RealmStringObject> getDays() {
        return days;
    }

    public void setDays(RealmList<RealmStringObject> days) {
        this.days = days;
    }
}

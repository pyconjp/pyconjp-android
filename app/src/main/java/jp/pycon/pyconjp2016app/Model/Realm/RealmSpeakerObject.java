package jp.pycon.pyconjp2016app.Model.Realm;

import io.realm.RealmObject;

/**
 * Created by rhoboro on 7/16/16.
 */
public class RealmSpeakerObject extends RealmObject {
    public String speaker;

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}

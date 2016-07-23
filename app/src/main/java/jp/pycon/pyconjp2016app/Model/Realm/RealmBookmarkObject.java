package jp.pycon.pyconjp2016app.Model.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by rhoboro on 7/23/16.
 */
public class RealmBookmarkObject extends RealmObject {
    private RealmList<RealmPresentationObject> bookmarks;

    public RealmList<RealmPresentationObject> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(RealmList<RealmPresentationObject> bookmarks) {
        this.bookmarks = bookmarks;
    }
}
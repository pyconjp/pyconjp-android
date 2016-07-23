package jp.pycon.pyconjp2016app.Util;

import android.content.Context;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationDetailEntity;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationEntity;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationListEntity;
import jp.pycon.pyconjp2016app.Model.Realm.RealmDaysObject;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationDetailObject;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.Model.Realm.RealmStringObject;

/**
 * Created by rhoboro on 7/23/16.
 */
public class RealmUtil {

    public static void savePresentationList(Context context, Realm realm, PresentationListEntity presentations) {
        // 前回結果を Realm から削除
        final RealmResults<RealmPresentationObject> results = realm.where(RealmPresentationObject.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });

        final RealmResults<RealmDaysObject> dayResults = realm.where(RealmDaysObject.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                dayResults.deleteAllFromRealm();
            }
        });

        List<Integer> bookmarks = PreferencesManager.getBookmark(context);
        // Realm用のビューモデルに変換してから格納する
        realm.beginTransaction();
        for (PresentationEntity presentation: presentations.presentations) {
            RealmPresentationObject obj = realm.createObject(RealmPresentationObject.class);
            obj.pk = presentation.pk;
            obj.title = presentation.title;
            obj.time = "22:26";
            obj.rooms = presentation.rooms;
            obj.day = dummyDay();
            RealmList<RealmStringObject> speakers = new RealmList<>();
            for (String speaker : presentation.speakers) {
                RealmStringObject speakerObject = realm.createObject(RealmStringObject.class);
                speakerObject.setString(speaker);
                speakers.add(speakerObject);
            }
            obj.speakers = speakers;
            if (bookmarks.contains(obj.pk)) {
                obj.bookmark = true;
            }

            RealmDaysObject days = realm.where(RealmDaysObject.class).findFirst();
            RealmStringObject day = realm.createObject(RealmStringObject.class);
            day.setString(obj.day);
            if (days != null) {
                boolean isExist = false;
                for (RealmStringObject str : days.getDays()) {
                    if (str.getString().equals(day.getString())) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    days.getDays().add(day);
                }
            } else {
                days = realm.createObject(RealmDaysObject.class);
                days.getDays().add(day);
            }
        }
        realm.commitTransaction();
    }

    public static void savePresentationDetail(Realm realm, int pk, PresentationDetailEntity detail) {
        RealmResults<RealmPresentationObject> results = realm.where(RealmPresentationObject.class)
                .equalTo("pk", pk)
                .findAll();
        realm.beginTransaction();
        RealmPresentationDetailObject obj = realm.createObject(RealmPresentationDetailObject.class);
        obj.title = results.get(0).title;
        obj.pk = pk;
        obj.description = detail.description;
        obj.abst = detail.abst;
        RealmList<RealmStringObject> speakers = new RealmList<>();
        for (String speaker : detail.speakers) {
            RealmStringObject speakerObject = realm.createObject(RealmStringObject.class);
            speakerObject.setString(speaker);
            speakers.add(speakerObject);
        }
        obj.speakers = speakers;
        realm.commitTransaction();
    }

    public static RealmResults<RealmPresentationObject> getAllTalks(Realm realm, int position) {
        RealmResults<RealmPresentationObject> results;
        RealmDaysObject days = realm.where(RealmDaysObject.class).findFirst();
        String day = days.getDays().get(position).getString();
        results = realm.where(RealmPresentationObject.class)
                .equalTo("day", day)
                .findAll();
        return results;
    }

    public static RealmResults<RealmPresentationObject> getBookmarkTalks(Context context, Realm realm, int position) {
        RealmResults<RealmPresentationObject> results;
        RealmDaysObject days = realm.where(RealmDaysObject.class).findFirst();
        String day = days.getDays().get(position).getString();
        // ブックマーク登録しているもののみ取得
        List<Integer> list = PreferencesManager.getBookmark(context);
        RealmQuery<RealmPresentationObject> query = realm.where(RealmPresentationObject.class);
        results = query.equalTo("day", day)
                .equalTo("bookmark", true)
                .findAll();
        return results;
    }

    public static boolean isTalkDetailExist(Realm realm, int pk) {
        RealmResults<RealmPresentationDetailObject> results = realm.where(RealmPresentationDetailObject.class)
                .equalTo("pk", pk)
                .findAll();
        return results.size() > 0;
    }
    public static RealmPresentationDetailObject getTalkDetail(Realm realm, int pk) {
        return realm.where(RealmPresentationDetailObject.class)
                .equalTo("pk", pk)
                .findFirst();
    }
    private static String dummyDay() {
        Random r = new Random();
        int i = r.nextInt(2);
        String day = "";
        switch (i) {
            case 0:
                day = "2016-09-21";
                break;
            case 1:
                day = "2016-09-22";
                break;
            case 2:
                day = "2016-09-23";
                break;
        }
        return day;
    }
}

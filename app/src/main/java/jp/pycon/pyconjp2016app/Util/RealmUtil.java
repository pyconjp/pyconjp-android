package jp.pycon.pyconjp2016app.Util;

import android.content.Context;

import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationDetailEntity;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationEntity;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationListEntity;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationDetailObject;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2016app.Model.Realm.RealmSpeakerObject;

/**
 * Created by rhoboro on 7/23/16.
 */
public class RealmUtil {

    public static void savePresentationList(Realm realm, PresentationListEntity presentations) {
        // 前回結果を Realm から削除
        final RealmResults<RealmPresentationObject> results = realm.where(RealmPresentationObject.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });

        // Realm用のビューモデルに変換してから格納する
        realm.beginTransaction();
        for (PresentationEntity presentation: presentations.presentations) {
            RealmPresentationObject obj = realm.createObject(RealmPresentationObject.class);
            obj.pk = presentation.pk;
            obj.title = presentation.title;
            obj.time = "22:26";
            obj.rooms = presentation.rooms;
            obj.day = dummyDay();
            RealmList<RealmSpeakerObject> speakers = new RealmList<>();
            for (String speaker : presentation.speakers) {
                RealmSpeakerObject speakerObject = realm.createObject(RealmSpeakerObject.class);
                speakerObject.speaker = speaker;
                speakers.add(speakerObject);
            }
            obj.speakers = speakers;
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
        RealmList<RealmSpeakerObject> speakers = new RealmList<>();
        for (String speaker : detail.speakers) {
            RealmSpeakerObject speakerObject = realm.createObject(RealmSpeakerObject.class);
            speakerObject.speaker = speaker;
            speakers.add(speakerObject);
        }
        obj.speakers = speakers;
        realm.commitTransaction();
    }

    public static RealmResults<RealmPresentationObject> getAllTalks(Realm realm, int position) {
        RealmResults<RealmPresentationObject> results;
        String date = position == 0 ? "2016-09-21" : "2016-09-22";
        results = realm.where(RealmPresentationObject.class)
                .equalTo("day", date)
                .findAll();
        return results;
    }

    public static RealmResults<RealmPresentationObject> getBookmarkTalks(Context context, Realm realm, int position) {
        RealmResults<RealmPresentationObject> results;
        String date = position == 0 ? "2016-09-21" : "2016-09-22";
        // ブックマーク登録しているもののみ取得
        List<Integer> list = PreferencesManager.getBookmark(context);
        RealmQuery<RealmPresentationObject> query = realm.where(RealmPresentationObject.class);
        query.equalTo("pk", -1);
        for (int i = 0; i < list.size(); i++) {
            if (i != 0) {
                query.or();
            }
            query.equalTo("pk", (int) list.get(i));
        }
        results = query.equalTo("day", date).findAll();
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
        return i == 0 ? "2016-09-21" : "2016-09-22";
    }
}

package jp.pycon.pyconjp2016app.Util;

import android.content.Context;

import java.util.List;

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
 *
 * RealmのDBを扱う処理を行うクラス
 */
public class RealmUtil {

    /**
     * トーク一覧をRealmデータベースに保存します
     * @param context コンテキスト
     * @param realm Realmインスタンス
     * @param presentations APIから取得したトーク一覧
     */
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
            obj.start = presentation.start;
            obj.end = presentation.end;
            obj.dispStart = DateUtil.toTimeFormattedString(presentation.start);
            obj.rooms = presentation.rooms;
            obj.language = presentation.language;
            obj.day = presentation.day;
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

            // 日付は動的に取得したものをあとでタブで利用
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
        obj.language = detail.language;
        obj.rooms = detail.rooms;
        obj.start = detail.start;
        obj.end = detail.end;
        obj.day = detail.day;
        obj.dispDate = DateUtil.toStartToEndFormattedString(detail.day, detail.start, detail.end);
        RealmList<RealmStringObject> speakers = new RealmList<>();
        for (String speaker : detail.speakers) {
            RealmStringObject speakerObject = realm.createObject(RealmStringObject.class);
            speakerObject.setString(speaker);
            speakers.add(speakerObject);
        }
        obj.speakers = speakers;
        realm.commitTransaction();
    }

    /**
     * タブを指定してトーク一覧を取得します
     * @param realm Realmインスタンス
     * @param position 日付タブのポジション
     * @return 指定されたタブのトーク一覧
     */
    public static RealmResults<RealmPresentationObject> getAllTalks(Realm realm, int position) {
        RealmResults<RealmPresentationObject> results;
        RealmDaysObject days = realm.where(RealmDaysObject.class).findFirst();
        String day = days.getDays().get(position).getString();
        results = realm.where(RealmPresentationObject.class)
                .equalTo("day", day)
                .findAll();
        return results;
    }

    /**
     * タブを指定してブックマークに保存されているトーク一覧を取得します
     * @param context コンテキスト
     * @param realm Realmインスタンス
     * @param position 日付タブのポジション
     * @return 指定されたタブのブックマークに保存されているトーク一覧
     */
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

    /**
     * 指定したトークの詳細情報があるかどうかを返します
     * @param realm Realmインスタンス
     * @param pk トークのPK
     * @return 指定したトークの詳細情報があればtrue
     */
    public static boolean isTalkDetailExist(Realm realm, int pk) {
        RealmResults<RealmPresentationDetailObject> results = realm.where(RealmPresentationDetailObject.class)
                .equalTo("pk", pk)
                .findAll();
        return results.size() > 0;
    }

    /**
     * トークの詳細情報を取得します
     * @param realm Realmインスタンス
     * @param pk トークのPK
     * @return 指定したトークの詳細情報
     */
    public static RealmPresentationDetailObject getTalkDetail(Realm realm, int pk) {
        return realm.where(RealmPresentationDetailObject.class)
                .equalTo("pk", pk)
                .findFirst();
    }
}

package jp.pycon.pyconjp2017app.Util;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import jp.pycon.pyconjp2017app.Model.PyConJP.PresentationDetailEntity;
import jp.pycon.pyconjp2017app.Model.PyConJP.PresentationEntity;
import jp.pycon.pyconjp2017app.Model.PyConJP.PresentationListEntity;
import jp.pycon.pyconjp2017app.Model.PyConJP.PresentationSpeakerInformationEntity;
import jp.pycon.pyconjp2017app.Model.Realm.RealmDaysObject;
import jp.pycon.pyconjp2017app.Model.Realm.RealmPresentationDetailObject;
import jp.pycon.pyconjp2017app.Model.Realm.RealmPresentationObject;
import jp.pycon.pyconjp2017app.Model.Realm.RealmSpeakerInformationObject;
import jp.pycon.pyconjp2017app.Model.Realm.RealmStringObject;

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
    public static void saveTalkList(Context context, Realm realm, PresentationListEntity presentations) {

        RealmUtil.deleteTalkList(context, realm);
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
            obj.type = RealmPresentationObject.TYPE_TALK;
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

    public static void deleteTalkList(Context context, Realm realm) {
        // 前回結果を Realm から削除
        RealmUtil.deletePresentationDetails(realm);
        final RealmResults<RealmPresentationObject> results = realm.where(RealmPresentationObject.class)
                .equalTo("type", RealmPresentationObject.TYPE_TALK)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    /**
     * ポスター一覧をRealmデータベースに保存します
     * @param context コンテキスト
     * @param realm Realmインスタンス
     * @param presentations APIから取得したトーク一覧
     */
    public static void savePosterList(Context context, Realm realm, PresentationListEntity presentations) {

        RealmUtil.deletePosterList(realm);

        // Realm用のビューモデルに変換してから格納する
        realm.beginTransaction();
        for (PresentationEntity presentation: presentations.presentations) {
            RealmPresentationObject obj = realm.createObject(RealmPresentationObject.class);
            obj.type = RealmPresentationObject.TYPE_POSTER;
            obj.pk = presentation.pk;
            obj.title = presentation.title;
            obj.language = presentation.language;
            obj.rooms = "Information gallery";
            RealmList<RealmStringObject> speakers = new RealmList<>();
            for (String speaker : presentation.speakers) {
                RealmStringObject speakerObject = realm.createObject(RealmStringObject.class);
                speakerObject.setString(speaker);
                speakers.add(speakerObject);
            }
            obj.speakers = speakers;
        }
        realm.commitTransaction();
    }

    public static void deletePosterList(Realm realm) {
        // 前回結果を Realm から削除
        RealmUtil.deletePresentationDetails(realm);
        final RealmResults<RealmPresentationObject> results = realm.where(RealmPresentationObject.class)
                .equalTo("type", RealmPresentationObject.TYPE_POSTER)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public static void deletePresentationDetails(Realm realm) {
        final RealmResults<RealmPresentationDetailObject> results = realm.where(RealmPresentationDetailObject.class)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    public static void savePresentationDetail(Realm realm, int pk, PresentationDetailEntity detail) {
        realm.beginTransaction();
        RealmPresentationDetailObject obj = realm.createObject(RealmPresentationDetailObject.class);
        obj.title = detail.title;
        obj.pk = pk;
        obj.description = detail.description;
        obj.abst = detail.abst;
        obj.language = detail.language;
        obj.rooms = detail.rooms;
        obj.start = detail.start;
        obj.end = detail.end;
        obj.day = detail.day;
        obj.level = detail.level;
        obj.category = detail.category;
        obj.dispDate = DateUtil.toStartToEndFormattedString(detail.day, detail.start, detail.end);
        RealmList<RealmStringObject> speakers = new RealmList<>();
        for (String speaker : detail.speakers) {
            RealmStringObject speakerObject = realm.createObject(RealmStringObject.class);
            speakerObject.setString(speaker);
            speakers.add(speakerObject);
        }
        obj.speakers = speakers;
        RealmList<RealmSpeakerInformationObject> speakerInformation = new RealmList<>();
        for (PresentationSpeakerInformationEntity info : detail.speakerInfomations) {
            RealmSpeakerInformationObject realmInfo = realm.createObject(RealmSpeakerInformationObject.class);
            realmInfo.name = info.name;
            realmInfo.imageUri = info.imageUri;
            realmInfo.twitter = info.twitter;
            speakerInformation.add(realmInfo);
        }
        obj.speakerInformation = speakerInformation;
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
                .equalTo("type", RealmPresentationObject.TYPE_TALK)
                .equalTo("day", day)
                .findAllSorted("start", Sort.ASCENDING, "rooms", Sort.ASCENDING);
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
                .equalTo("type", RealmPresentationObject.TYPE_TALK)
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
     * ポスター一覧を取得します
     * @param realm Realmインスタンス
     * @return ポスター一覧
     */
    public static RealmResults<RealmPresentationObject> getAllPosters(Realm realm) {
        RealmResults<RealmPresentationObject> results;
        results = realm.where(RealmPresentationObject.class)
                .equalTo("type", RealmPresentationObject.TYPE_POSTER)
                .findAll();
        return results;
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

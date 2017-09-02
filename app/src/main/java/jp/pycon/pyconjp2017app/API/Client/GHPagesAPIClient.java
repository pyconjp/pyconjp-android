package jp.pycon.pyconjp2017app.API.Client;

import jp.pycon.pyconjp2017app.Model.GHPages.KeynoteListEntity;
import jp.pycon.pyconjp2017app.Model.GHPages.StaffListEntity;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by rhoboro on 8/2/16.
 */
public interface GHPagesAPIClient {

    @GET("keynotes.json")
    Observable<KeynoteListEntity> getKeynotes();

    @GET("stafflist.json")
    Observable<StaffListEntity> getStaffList();
}

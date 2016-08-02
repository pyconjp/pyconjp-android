package jp.pycon.pyconjp2016app.API.Client;

import jp.pycon.pyconjp2016app.Model.GHPages.KeynoteListEntity;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by rhoboro on 8/2/16.
 */
public interface GHPagesAPIClient {

    String BASE_URL = "https://pyconjp.github.io/pyconjp-android/";

    @GET("keynotes.json")
    Observable<KeynoteListEntity> getKeynotes();
}

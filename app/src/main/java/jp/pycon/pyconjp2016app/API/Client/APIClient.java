package jp.pycon.pyconjp2016app.API.Client;

import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationDetailEntity;
import jp.pycon.pyconjp2016app.Model.PyConJP.PresentationListEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rhoboro on 7/5/16.
 */
public interface APIClient {

    String BASE_URL = "http://pycon.jp/2016/ja/api/";

    @GET("talks/list/")
    Observable<PresentationListEntity> getPyConJPTalks();
    @GET("posters/list/")
    Observable<PresentationListEntity> getPyConJPPosters();
    @GET("tutorials/list/")
    Observable<PresentationListEntity> getPyConJPTutorials();
    @GET("presentation/{pk}/")
    Observable<PresentationDetailEntity> getPyConJPPresentationDetail(@Path("pk") int pk);

}

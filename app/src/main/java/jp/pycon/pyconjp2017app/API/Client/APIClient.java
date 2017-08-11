package jp.pycon.pyconjp2017app.API.Client;

import jp.pycon.pyconjp2017app.Model.PyConJP.PresentationDetailEntity;
import jp.pycon.pyconjp2017app.Model.PyConJP.PresentationListEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rhoboro on 7/5/16.
 */
public interface APIClient {

    @GET("talks/list/")
    Observable<PresentationListEntity> getPyConJPTalks();
    @GET("posters/list/")
    Observable<PresentationListEntity> getPyConJPPosters();
    @GET("presentation/{pk}/")
    Observable<PresentationDetailEntity> getPyConJPPresentationDetail(@Path("pk") int pk);

}

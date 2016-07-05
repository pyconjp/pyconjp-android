package jp.pycon.pyconjp2016app.API.Client;

import jp.pycon.pyconjp2016app.API.Entity.PyConJPScheduleEntity;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by rhoboro on 7/5/16.
 */
public interface APIClient {

    @GET("pyconjp_schedule.json")
    Observable<PyConJPScheduleEntity> getPyConJPSchedule();
}

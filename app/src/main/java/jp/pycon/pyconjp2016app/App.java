package jp.pycon.pyconjp2016app;

import android.app.Application;

import com.deploygate.sdk.DeployGate;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import jp.pycon.pyconjp2016app.API.Client.APIClient;
import jp.pycon.pyconjp2016app.API.Client.LocalResponseInterceptor;
import jp.pycon.pyconjp2016app.Model.Realm.RealmPresentationObject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rhoboro on 4/3/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DeployGate.install(this);

        // TODO: リリース時には deleteRealmIfMigrationNeeded を消す
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
//                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public APIClient getAPIClient() {
        Retrofit retrofit;
        if (BuildConfig.PRODUCTION) {
            // 本番APIを叩く
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        } else {
            // ローカルのサンプルファイルを利用する
            LocalResponseInterceptor i = new LocalResponseInterceptor(getApplicationContext());
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(i)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit.create(APIClient.class);
    }
}

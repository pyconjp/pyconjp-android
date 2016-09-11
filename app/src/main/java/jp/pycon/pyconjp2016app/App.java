package jp.pycon.pyconjp2016app;

import android.app.Application;

import com.deploygate.sdk.DeployGate;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import jp.pycon.pyconjp2016app.API.Client.APIClient;
import jp.pycon.pyconjp2016app.API.Client.GHPagesAPIClient;
import jp.pycon.pyconjp2016app.API.Client.LocalResponseInterceptor;
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
        setUpRealm();
    }

    private void setUpRealm() {
        RealmMigration migration = new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

                // DynamicRealmからは変更可能なスキーマインスタンスを取得できます
                RealmSchema schema = realm.getSchema();

                if (oldVersion == 0) {
                    // バージョン1への移行処理
                    oldVersion++;
                }

                if (oldVersion == 1) {
                    // バージョン2への移行処理
                    schema.create("RealmSpeakerInformationObject")
                            .addField("name", String.class)
                            .addField("twitter", String.class)
                            .addField("imageUri", String.class);
                    schema.get("RealmPresentationDetailObject")
                            .addRealmListField("speakerInformation",schema.get("RealmSpeakerInformationObject"));
                }
            }
        };

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .schemaVersion(2)
                .migration(migration)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    /**
     * PyConJPへアクセスするAPIクライアントを返します
     * @return PyConJPへアクセスするAPIクライアント
     */
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

    /**
     * GitHubPagesへアクセスするAPIクライアントを返します
     * @return GitHubPagesへアクセスするAPIクライアント
     */
    public GHPagesAPIClient getGHPagesAPIClient() {
        Retrofit retrofit;
        if (BuildConfig.PRODUCTION) {
            // 本番APIを叩く
            retrofit = new Retrofit.Builder()
                    .baseUrl(GHPagesAPIClient.BASE_URL)
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
                    .baseUrl(GHPagesAPIClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit.create(GHPagesAPIClient.class);
    }
}

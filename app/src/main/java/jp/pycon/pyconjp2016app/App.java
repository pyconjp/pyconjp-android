package jp.pycon.pyconjp2016app;

import android.app.Application;

import com.deploygate.sdk.DeployGate;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
}

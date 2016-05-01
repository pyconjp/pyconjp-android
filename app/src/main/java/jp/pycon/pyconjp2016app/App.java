package jp.pycon.pyconjp2016app;

import android.app.Application;

import com.deploygate.sdk.DeployGate;

/**
 * Created by rhoboro on 4/3/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DeployGate.install(this);
    }
}

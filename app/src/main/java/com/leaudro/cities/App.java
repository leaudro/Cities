package com.leaudro.cities;

import android.app.Application;

public class App extends Application {

    public static App appInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;
    }
}

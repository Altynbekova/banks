package com.altynbekova.banks;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey("d73b7524-32d0-4a50-af54-5fc97875ccc7");
    }
}

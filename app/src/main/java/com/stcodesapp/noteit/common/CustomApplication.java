package com.stcodesapp.noteit.common;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.dependencyInjection.CompositionRoot;

public class CustomApplication extends Application {

    private CompositionRoot compositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        compositionRoot = new CompositionRoot();
        MobileAds.initialize(this, this.getResources().getString(R.string.admob_app_id));

    }

    public CompositionRoot getCompositionRoot() {
        return compositionRoot;
    }
}

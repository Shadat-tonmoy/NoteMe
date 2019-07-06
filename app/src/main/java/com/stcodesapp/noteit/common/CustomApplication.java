package com.stcodesapp.noteit.common;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.common.adController.FullScreenAdController;
import com.stcodesapp.noteit.common.dependencyInjection.CompositionRoot;
import io.fabric.sdk.android.Fabric;

public class CustomApplication extends Application {

    private CompositionRoot compositionRoot;
    private FullScreenAdController fullScreenAdController;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        compositionRoot = new CompositionRoot();
        MobileAds.initialize(this, this.getResources().getString(R.string.admob_app_id));

    }

    public void setFullScreenAdController(FragmentActivity activity)
    {
        fullScreenAdController = compositionRoot.getActivityControllerFactory(activity).getFullScreenAdController();
        fullScreenAdController.onStart();

    }

    public FullScreenAdController getFullScreenAdController()
    {
        return fullScreenAdController;
    }

    public CompositionRoot getCompositionRoot() {
        return compositionRoot;
    }
}

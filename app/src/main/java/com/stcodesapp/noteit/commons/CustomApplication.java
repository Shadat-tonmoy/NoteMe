package com.stcodesapp.noteit.commons;

import android.app.Application;

import com.stcodesapp.noteit.commons.dependencyInjection.CompositionRoot;

public class CustomApplication extends Application {
    private CompositionRoot compositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        compositionRoot = new CompositionRoot();
    }

    public CompositionRoot getCompositionRoot() {
        return compositionRoot;
    }
}

package com.stcodesapp.noteit.commons.dependencyInjection;

import android.app.Activity;

import com.stcodesapp.noteit.factory.ControllerFactory;
import com.stcodesapp.noteit.factory.ViewFactory;

public class ControllerCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final Activity activity;

    public ControllerCompositionRoot(CompositionRoot mCompositionRoot, Activity activity) {
        this.mCompositionRoot = mCompositionRoot;
        this.activity = activity;
    }

    public ViewFactory getViewFactory()
    {
        return mCompositionRoot.getViewFactory();
    }

    public ControllerFactory getControllerFactory()
    {
        return mCompositionRoot.getControllerFactory(activity);
    }
}

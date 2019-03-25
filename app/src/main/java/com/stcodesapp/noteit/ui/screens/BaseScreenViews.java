package com.stcodesapp.noteit.ui.screens;

import android.content.Context;
import android.view.View;

public abstract class BaseScreenViews implements BaseScreen{

    View rootView;


    public View getRootView() {
        return rootView;
    }

    protected <T extends View> T findViewById(int id)
    {
        return getRootView().findViewById(id);
    }

    protected Context getContext()
    {
        return getRootView().getContext();
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }
}

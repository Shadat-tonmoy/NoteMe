package com.stcodesapp.noteit.ui.views.baseScreens;

import android.content.Context;
import android.view.View;

public abstract class BaseScreenView implements BaseScreen {

    private View mRootView;

    @Override
    public View getRootView() {
        return mRootView;
    }

    public void setRootView(View mRootView) {
        this.mRootView = mRootView;
    }

    protected Context getContext()
    {
        return getRootView().getContext();
    }

    protected  <T extends View> T findViewById(int id)
    {
        return getRootView().findViewById(id);
    }
}

package com.stcodesapp.noteit.ui.views.screens.commons;

import android.widget.FrameLayout;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface NavigationDrawerScreen extends BaseObservableScreen<NavigationDrawerScreen.Listener> {

    interface Listener{
        void onNavigationDrawerItemClicked(int itemId);
    }


    FrameLayout getFragmentFrame();
}

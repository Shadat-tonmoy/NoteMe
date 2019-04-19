package com.stcodesapp.noteit.ui.views.screens.activityScreen;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface ManualContactScreen extends BaseObservableScreen<ManualContactScreen.Listener> {

    interface Listener{

        void onDoneButtonClicked();

        void onNavigateUp();

    }
}

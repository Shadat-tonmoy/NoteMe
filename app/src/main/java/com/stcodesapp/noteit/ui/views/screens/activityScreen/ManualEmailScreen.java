package com.stcodesapp.noteit.ui.views.screens.activityScreen;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface ManualEmailScreen extends BaseObservableScreen<ManualEmailScreen.Listener> {

    interface Listener{

        void onDoneButtonClicked();

        void onNavigateUp();

    }
}

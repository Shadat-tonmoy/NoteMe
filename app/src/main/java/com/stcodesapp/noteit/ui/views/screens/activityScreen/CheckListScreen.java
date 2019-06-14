package com.stcodesapp.noteit.ui.views.screens.activityScreen;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface CheckListScreen extends BaseObservableScreen<CheckListScreen.Listener> {

    interface Listener{

        void onNavigateUp();

        void onCheckListDoneButtonClicked();

    }
}

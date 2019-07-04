package com.stcodesapp.noteit.ui.views.screens.activityScreen;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface IAPScreen extends BaseObservableScreen<IAPScreen.Listener> {

    interface Listener{

        void onNavigateUp();

        void onMonthlySubsClicked();

        void onHalfYearlySubsClicked();

        void onYearlySubsClicked();

        void onLifetimeSubsClicked();

        void onDoneButtonClicked();

    }
}

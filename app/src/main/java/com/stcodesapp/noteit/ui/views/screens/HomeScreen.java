package com.stcodesapp.noteit.ui.views.screens;


import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface HomeScreen extends BaseObservableScreen<HomeScreen.Listener> {

    interface Listener {

        void onOptionMenuClicked(int menuId);

        void onNoteAddButtonClicked();

    }


}

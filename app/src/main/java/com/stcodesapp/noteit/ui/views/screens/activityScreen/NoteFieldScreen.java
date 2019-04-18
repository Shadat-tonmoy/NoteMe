package com.stcodesapp.noteit.ui.views.screens.activityScreen;

import android.view.MenuItem;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface NoteFieldScreen extends BaseObservableScreen<NoteFieldScreen.Listener> {

    interface Listener{

        void onNavigateUp();

        void onOptionItemSelected(MenuItem item);
    }


}

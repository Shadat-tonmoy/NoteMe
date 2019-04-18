package com.stcodesapp.noteit.ui.views.screens.activityScreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface NoteFieldScreen extends BaseObservableScreen<NoteFieldScreen.Listener> {

    interface Listener{

        void onNavigateUp();

        void onOptionItemSelected(MenuItem item);

        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
    }


}

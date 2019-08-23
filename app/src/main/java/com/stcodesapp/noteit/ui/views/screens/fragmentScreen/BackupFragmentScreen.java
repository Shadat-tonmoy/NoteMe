package com.stcodesapp.noteit.ui.views.screens.fragmentScreen;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface BackupFragmentScreen extends BaseObservableScreen<BackupFragmentScreen.Listener> {

    interface Listener
    {
        void onBackupToLocalStorageClicked();

        void onBackupToCloudStorageClicked();

        void onRestoreFromLocalStorageClicked();

        void onRestoreFromCloudStorageClicked();

        void onLocalBackupStorageOptionSelected(int checkedID);

    }


}

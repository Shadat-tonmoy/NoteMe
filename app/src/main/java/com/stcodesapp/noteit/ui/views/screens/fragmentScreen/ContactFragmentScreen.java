package com.stcodesapp.noteit.ui.views.screens.fragmentScreen;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface ContactFragmentScreen extends BaseObservableScreen<ContactFragmentScreen.Listener> {

    interface Listener {

        void onCallButtonPressed();

        void onCopyButtonPressed();

    }


}

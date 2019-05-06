package com.stcodesapp.noteit.ui.views.screens.activityScreen;

import android.widget.Button;
import android.widget.EditText;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface ManualEmailScreen extends BaseObservableScreen<ManualEmailScreen.Listener> {

    interface Listener{

        void onDoneButtonClicked();

        void onNavigateUp();
    }

    EditText getNameField();

    EditText getEmailAddressField();

    Button getContinueButton();
}

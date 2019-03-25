package com.stcodesapp.noteit.ui;

import android.widget.EditText;

import com.stcodesapp.noteit.ui.screens.UIScreen;

public interface InputFieldUI extends UIScreen {

    interface InputFieldListener{
        void onInputClear(EditText editText);

        void onInputSubmit(String noteText);
    }

    void setInputFieldListener(InputFieldListener inputFieldListener);

    void generateUI();


}

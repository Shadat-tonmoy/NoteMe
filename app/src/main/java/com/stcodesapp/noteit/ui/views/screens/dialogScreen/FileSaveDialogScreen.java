package com.stcodesapp.noteit.ui.views.screens.dialogScreen;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreen;

public interface FileSaveDialogScreen extends BaseObservableScreen<FileSaveDialogScreen.Listener> {

    interface Listener
    {
        void onPositiveButtonClicked();

        void onNegativeButtonClicked(boolean saveFileToDB);

        void onEditTextChanged(String text);
    }

    EditText getFileNameField();

    TextView getSaveButton();

    TextView getFileExistsText();

    TextView getFilePathText();

    TextView getFileSaveDoneText();

    LinearLayout getFileSaveDoneLayout();
}

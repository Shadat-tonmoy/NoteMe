package com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.FileSaveDialogScreen;



public class FileSaveDialogScreenView extends BaseObservableScreenView<FileSaveDialogScreen.Listener> implements FileSaveDialogScreen {

    private TextView cancelButton,saveButton,fileExistsText,fileSaveDoneText;
    private EditText fileNameField;
    private LinearLayout fileSaveDoneLayout;

    public FileSaveDialogScreenView(LayoutInflater inflater, @Nullable ViewGroup parent)
    {
        setRootView(inflater.inflate(R.layout.file_save_dialog_screen,parent,false));
        inflateUIElements();
        initUserInteractions();
    }


    @Override
    public void initUserInteractions() {
        setClickListener(cancelButton, EventTypes.SAVE_DIALOG_CANCEL_BUTTON_CLICKED);
        setClickListener(saveButton,EventTypes.SAVE_DIALOG_SAVE_BUTTON_CLICKED);
        setTextChangeListener();

    }

    @Override
    public void inflateUIElements() {
        cancelButton = findViewById(R.id.cancel_button);
        saveButton = findViewById(R.id.save_button);
        fileNameField = findViewById(R.id.file_name_field);
        fileExistsText = findViewById(R.id.file_exists_text);
        fileSaveDoneLayout = findViewById(R.id.file_save_done_panel);
        fileSaveDoneText = findViewById(R.id.file_save_done_text);

    }

    private void setClickListener(View view, final int eventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {
                    switch (eventType)
                    {
                        case EventTypes.SAVE_DIALOG_CANCEL_BUTTON_CLICKED:
                            listener.onNegativeButtonClicked(false);
                            break;
                        case EventTypes.SAVE_DIALOG_SAVE_BUTTON_CLICKED:
                            listener.onPositiveButtonClicked();
                            break;
                    }
                }
            }
        });
    }

    private void setTextChangeListener()
    {
        fileNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(Listener listener:getListeners())
                {
                    listener.onEditTextChanged(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public EditText getFileNameField() {
        return fileNameField;
    }

    @Override
    public TextView getSaveButton() {
        return saveButton;
    }

    @Override
    public TextView getFileExistsText() {
        return fileExistsText;
    }

    @Override
    public TextView getFileSaveDoneText() {
        return fileSaveDoneText;
    }

    @Override
    public LinearLayout getFileSaveDoneLayout() {
        return fileSaveDoneLayout;
    }
}

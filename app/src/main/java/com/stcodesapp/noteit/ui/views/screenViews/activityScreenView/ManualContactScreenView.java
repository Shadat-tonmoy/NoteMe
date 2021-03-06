package com.stcodesapp.noteit.ui.views.screenViews.activityScreenView;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.ManualContactScreen;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.NoteFieldScreen;

public class ManualContactScreenView extends BaseObservableScreenView<ManualContactScreen.Listener> implements ManualContactScreen {


    private Toolbar toolbar;
    private EditText nameField, phoneNoField;
    private Button continueButton;

    public ManualContactScreenView(LayoutInflater inflater, @Nullable ViewGroup parent)
    {
        setRootView(inflater.inflate(R.layout.manual_contact_screen,parent,false));
        inflateUIElements();
    }


    @Override
    public void initUserInteractions() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {
                    listener.onNavigateUp();
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener:getListeners())
                {
                    listener.onDoneButtonClicked();
                }
            }
        });

    }

    @Override
    public void inflateUIElements() {
        toolbar = findViewById(R.id.app_toolbar);
        toolbar.setTitle(getContext().getResources().getString(R.string.add_contact));
        toolbar.setNavigationIcon(getContext().getResources().getDrawable(R.drawable.back_white));

        nameField = findViewById(R.id.contact_name_field);
        phoneNoField = findViewById(R.id.contact_no_field);
        continueButton = findViewById(R.id.continue_button);

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public EditText getNameField() {
        return nameField;
    }

    public EditText getPhoneNoField() {
        return phoneNoField;
    }

    public Button getContinueButton() {
        return continueButton;
    }
}

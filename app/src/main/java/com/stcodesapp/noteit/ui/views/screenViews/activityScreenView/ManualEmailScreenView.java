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
import com.stcodesapp.noteit.ui.views.screens.activityScreen.ManualEmailScreen;

public class ManualEmailScreenView extends BaseObservableScreenView<ManualEmailScreen.Listener> implements ManualEmailScreen {


    private Toolbar toolbar;
    private EditText nameField, emailField;
    private Button continueButton;

    public ManualEmailScreenView(LayoutInflater inflater, @Nullable ViewGroup parent)
    {
        setRootView(inflater.inflate(R.layout.manual_email_screen,parent,false));
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
        toolbar.setTitle(getContext().getResources().getString(R.string.add_email));
        toolbar.setNavigationIcon(getContext().getResources().getDrawable(R.drawable.back_white));

        nameField = findViewById(R.id.email_name_field);
        emailField = findViewById(R.id.email_field);
        continueButton = findViewById(R.id.continue_button);

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public EditText getNameField() {
        return nameField;
    }

    public EditText getEmailField() {
        return emailField;
    }

    public Button getContinueButton() {
        return continueButton;
    }
}

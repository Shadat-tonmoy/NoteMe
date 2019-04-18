package com.stcodesapp.noteit.ui.views.screenViews.activityScreenView;

import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.NoteFieldScreen;


public class NoteFieldScreenView extends BaseObservableScreenView<NoteFieldScreen.Listener> implements NoteFieldScreen {


    private Toolbar toolbar;
    private TextView noteTitleText;
    private EditText noteTitleField,noteTextField;
    private CoordinatorLayout uiRoot;
    private View noteTitleDivider,noteTextDivider;
    private LinearLayout uiComponentContainer;

    public NoteFieldScreenView(LayoutInflater inflater, @Nullable ViewGroup parent)
    {
        setRootView(inflater.inflate(R.layout.note_field_screen,parent,false));
        inflateUIElements();
    }




    @Override
    public void initUserInteractions()
    {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {
                    listener.onNavigateUp();
                }
            }
        });


    }

    @Override
    public void inflateUIElements() {

        toolbar = findViewById(R.id.app_toolbar);
        toolbar.setTitle(getContext().getResources().getString(R.string.empty_string));
        toolbar.setNavigationIcon(getContext().getResources().getDrawable(R.drawable.back_white));
        uiRoot = findViewById(R.id.ui_root);
        noteTitleText = findViewById(R.id.title_text);
        noteTitleField = findViewById(R.id.note_title_field);
        noteTextField = findViewById(R.id.note_text_field);
        noteTitleDivider = findViewById(R.id.note_title_divider);
        noteTextDivider = findViewById(R.id.note_text_divider);
        uiComponentContainer = findViewById(R.id.ui_component_container);


    }

    public TextView getNoteTitleText() {
        return noteTitleText;
    }

    public EditText getNoteTitleField() {
        return noteTitleField;
    }

    public EditText getNoteTextField() {
        return noteTextField;
    }

    public CoordinatorLayout getUiRoot() {
        return uiRoot;
    }

    public View getNoteTitleDivider() {
        return noteTitleDivider;
    }

    public View getNoteTextDivider() {
        return noteTextDivider;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public LinearLayout getUiComponentContainer() {
        return uiComponentContainer;
    }
}

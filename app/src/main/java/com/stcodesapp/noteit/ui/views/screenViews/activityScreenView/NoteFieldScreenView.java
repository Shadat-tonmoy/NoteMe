package com.stcodesapp.noteit.ui.views.screenViews.activityScreenView;

import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.NoteFieldScreen;

import java.util.List;



public class NoteFieldScreenView extends BaseObservableScreenView<NoteFieldScreen.Listener> implements NoteFieldScreen {


    private Toolbar toolbar;
    private TextView noteTitleText;
    private EditText noteTitleField,noteTextField;
    private CoordinatorLayout uiRoot;
    private View noteTitleDivider,noteTextDivider;
    private LinearLayout uiComponentContainer;
    private ImageView titleMic, noteMic;
    private ScrollView uiComponentRoot;
    private FloatingActionButton noteSaveButton;

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
        setClickListener(titleMic, EventTypes.NOTE_TITLE_MIC_CLICKED);
        setClickListener(noteMic, EventTypes.NOTE_TEXT_MIC_CLICKED);
        setClickListener(noteSaveButton, EventTypes.NOTE_SAVE_BUTTON_CLICKED);
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
        titleMic = findViewById(R.id.title_mic);
        noteMic = findViewById(R.id.note_mic);
        noteTitleDivider = findViewById(R.id.note_title_divider);
        noteTextDivider = findViewById(R.id.note_text_divider);
        noteSaveButton = findViewById(R.id.save_button);
        uiComponentContainer = findViewById(R.id.ui_component_container);
        uiComponentRoot = findViewById(R.id.ui_component_root);


    }

    private void setClickListener(View view, final int eventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Listener listener : getListeners())
                {
                    switch (eventType)
                    {
                        case EventTypes.NOTE_TITLE_MIC_CLICKED:
                            listener.onTitleMicClicked();
                            break;
                        case EventTypes.NOTE_TEXT_MIC_CLICKED:
                            listener.onNoteMicClicked();
                            break;
                        case EventTypes.NOTE_SAVE_BUTTON_CLICKED:
                            listener.onSaveButtonClicked();
                            break;
                        case EventTypes.NOTE_EXPORT_BUTTON_CLICKED:
                            listener.onExportAsButtonClicked();
                            break;
                    }
                }
            }
        });
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

    public ScrollView getUiComponentRoot() {
        return uiComponentRoot;
    }

}

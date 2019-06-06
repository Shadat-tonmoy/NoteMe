package com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.stcodesapp.noteit.BuildConfig;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.SortingType;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.SortingOptionDialogScreen;

public class SortingOptionDialogScreenView extends BaseObservableScreenView<SortingOptionDialogScreen.Listener> implements SortingOptionDialogScreen {

    private Spinner titleOptions, noteOptions, createdOptions, importantOptions;
    private boolean firstTime = true;
    private int eventCount = 0;

    public SortingOptionDialogScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.sorting_option_dialog_layout,parent,false));
        inflateUIElements();
        initUserInteractions();
    }




    @Override
    public void initUserInteractions() {
        setItemSelectedListener(titleOptions, SortingType.NOTE_TITLE);
        setItemSelectedListener(noteOptions, SortingType.NOTE_TEXT);
        setItemSelectedListener(createdOptions, SortingType.NOTE_TIME);
        setItemSelectedListener(importantOptions, SortingType.NOTE_IMPORTANT);

    }

    @Override
    public void inflateUIElements() {
        titleOptions = findViewById(R.id.note_title_choices);
        noteOptions = findViewById(R.id.note_text_choices);
        createdOptions = findViewById(R.id.note_time_choices);
        importantOptions = findViewById(R.id.note_important_choices);
    }

    private void setItemSelectedListener(Spinner spinner, final SortingType sortingType)
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (sortingType)
                {
                    case NOTE_TITLE:
                        if(!firstTime)
                            for (Listener listener:getListeners())
                                listener.onNoteTitleOptionSelected(position);
                        break;
                    case NOTE_TEXT:
                        if(!firstTime)
                            for (Listener listener:getListeners())
                                listener.onNoteTextOptionSelected(position);
                        break;
                    case NOTE_TIME:
                        if(!firstTime)
                            for (Listener listener:getListeners())
                                listener.onNoteTimeOptionSelected(position);
                        break;
                    case NOTE_IMPORTANT:
                        if(!firstTime)
                            for (Listener listener:getListeners())
                                listener.onNoteImportantOptionSelected(position);
                        break;
                }
                eventCount++;
                if(eventCount==4)
                    firstTime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public Spinner getTitleOptions() {
        return titleOptions;
    }

    public Spinner getNoteOptions() {
        return noteOptions;
    }

    public Spinner getCreatedOptions() {
        return createdOptions;
    }

    public Spinner getImportantOptions() {
        return importantOptions;
    }
}

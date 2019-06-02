package com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.SpinnerType;
import com.stcodesapp.noteit.tasks.filteringTasks.NoteFilteringTask;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.SortingOptionDialogScreen;

public class SortingOptionDialogScreenView extends BaseObservableScreenView<SortingOptionDialogScreen.Listener> implements SortingOptionDialogScreen {

    private Spinner titleOptions, noteOptions, createdOptions, importantOptions;

    public SortingOptionDialogScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.sorting_option_dialog_layout,parent,false));
        inflateUIElements();
        initUserInteractions();
    }




    @Override
    public void initUserInteractions() {
        setItemSelectedListener(titleOptions,SpinnerType.NOTE_TITLE);
        setItemSelectedListener(noteOptions,SpinnerType.NOTE_TEXT);
        setItemSelectedListener(createdOptions,SpinnerType.NOTE_TIME);
        setItemSelectedListener(importantOptions,SpinnerType.NOTE_IMPORTANT);

    }

    @Override
    public void inflateUIElements() {
        titleOptions = findViewById(R.id.note_title_choices);
        noteOptions = findViewById(R.id.note_text_choices);
        createdOptions = findViewById(R.id.note_time_choices);
        importantOptions = findViewById(R.id.note_important_choices);
    }

    private void setItemSelectedListener(Spinner spinner, final SpinnerType spinnerType)
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinnerType)
                {
                    case NOTE_TITLE:
                        Log.e("selected","title "+position);
                        break;
                    case NOTE_TEXT:
                        Log.e("selected","text "+position);
                        break;
                    case NOTE_TIME:
                        Log.e("selected","time "+position);
                        break;
                    case NOTE_IMPORTANT:
                        Log.e("selected","important "+position);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

package com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.tasks.filteringTasks.NoteFilteringTask;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.dialogScreen.SortingOptionDialogScreen;

public class SortingOptionDialogScreenView extends BaseObservableScreenView<SortingOptionDialogScreen.Listener> implements SortingOptionDialogScreen {

    public SortingOptionDialogScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.sorting_option_dialog_layout,parent,false));
        inflateUIElements();
        initUserInteractions();
    }




    @Override
    public void initUserInteractions() {

    }

    @Override
    public void inflateUIElements() {

    }
}

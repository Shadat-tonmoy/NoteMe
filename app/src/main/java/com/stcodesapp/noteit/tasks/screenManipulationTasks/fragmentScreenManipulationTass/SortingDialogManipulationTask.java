package com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass;

import android.app.Activity;
import android.os.Bundle;

import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.ui.views.screenViews.dialogScreenView.SortingOptionDialogScreenView;

public class SortingDialogManipulationTask {

    private Activity activity;
    private SortingOptionDialogScreenView sortingOptionDialogScreenView;

    public SortingDialogManipulationTask(Activity activity) {
        this.activity = activity;
    }

    public void bindView(SortingOptionDialogScreenView sortingOptionDialogScreenView) {
        this.sortingOptionDialogScreenView = sortingOptionDialogScreenView;
    }

    public void setSpinnerValueFromBundle(Bundle bundle)
    {
        boolean titleAsc = bundle.getBoolean(FragmentTags.TITILE_ASC,true);
        boolean textAsc = bundle.getBoolean(FragmentTags.TEXT_ASC,true);
        boolean timeAsc = bundle.getBoolean(FragmentTags.TIME_ASC,true);
        boolean importantAsc = bundle.getBoolean(FragmentTags.IMPORTANT_ASC,true);
        if(!titleAsc)
            sortingOptionDialogScreenView.getTitleOptions().setSelection(1);
        if(!textAsc)
            sortingOptionDialogScreenView.getNoteOptions().setSelection(1);
        if(!timeAsc)
            sortingOptionDialogScreenView.getCreatedOptions().setSelection(1);
        if(!importantAsc)
            sortingOptionDialogScreenView.getImportantOptions().setSelection(1);
    }
}

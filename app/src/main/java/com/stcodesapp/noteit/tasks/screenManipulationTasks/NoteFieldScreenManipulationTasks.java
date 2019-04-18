package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldScreenManipulationTasks {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;

    public NoteFieldScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(NoteFieldScreenView noteFieldScreenView) {
        this.noteFieldScreenView = noteFieldScreenView;
    }

    public void showColorPallate(ColorPallateBottomSheets.Listener listener)
    {
        ColorPallateBottomSheets colorPallateBottomSheets =  new ColorPallateBottomSheets();
        colorPallateBottomSheets.setListener(listener);
        colorPallateBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(),"TAG");

    }
}

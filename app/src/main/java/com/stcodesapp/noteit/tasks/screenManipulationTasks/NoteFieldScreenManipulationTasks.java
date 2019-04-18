package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.ui.fragments.ColorPallateBottomSheets;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.NoteFieldScreenView;

public class NoteFieldScreenManipulationTasks {

    private Activity activity;
    private NoteFieldScreenView noteFieldScreenView;
    private ColorPallateBottomSheets colorPallateBottomSheets;

    public NoteFieldScreenManipulationTasks(Activity activity) {
        this.activity = activity;
    }

    public void bindView(NoteFieldScreenView noteFieldScreenView) {
        this.noteFieldScreenView = noteFieldScreenView;
    }

    public void showColorPalate(ColorPallateBottomSheets.Listener listener)
    {
        colorPallateBottomSheets =  new ColorPallateBottomSheets();
        colorPallateBottomSheets.setListener(listener);
        colorPallateBottomSheets.show(((AppCompatActivity)activity).getSupportFragmentManager(),"TAG");
    }

    public void dismissColorPalate()
    {
        if(colorPallateBottomSheets!=null && colorPallateBottomSheets.isVisible())
            colorPallateBottomSheets.dismiss();
    }

    public void applyBackgroundColor(String colorName)
    {
        try {
            noteFieldScreenView.getUiRoot().setBackgroundColor(activity.getResources().getColor(BackgroundColors.getColorMaps().get(colorName)));
            int textColor = R.color.white;
            int dividerColor = R.color.white;
            if(colorName.equals(BackgroundColors.WHITE))
            {
                textColor = R.color.default_text;
                dividerColor = R.color.default_divider;
            }
            noteFieldScreenView.getNoteTitleText().setTextColor(activity.getResources().getColor(textColor));
            noteFieldScreenView.getNoteTextField().setTextColor(activity.getResources().getColor(textColor));
            noteFieldScreenView.getNoteTextField().setHintTextColor(activity.getResources().getColor(textColor));
            noteFieldScreenView.getNoteTitleDivider().setBackgroundColor(activity.getResources().getColor(dividerColor));
            noteFieldScreenView.getNoteTextDivider().setBackgroundColor(activity.getResources().getColor(dividerColor));
        }catch (Exception e)
        {
            Log.e("Exeption",e.getMessage());
        }



    }
}

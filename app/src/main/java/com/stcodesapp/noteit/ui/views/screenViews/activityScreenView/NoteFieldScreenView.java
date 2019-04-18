package com.stcodesapp.noteit.ui.views.screenViews.activityScreenView;

import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.NoteFieldScreen;


public class NoteFieldScreenView extends BaseObservableScreenView<NoteFieldScreen.Listener> implements NoteFieldScreen {


    private Toolbar toolbar;


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


    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}

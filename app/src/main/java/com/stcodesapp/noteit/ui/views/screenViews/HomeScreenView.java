package com.stcodesapp.noteit.ui.views.screenViews;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.HomeScreen;

public class HomeScreenView extends BaseObservableScreenView<HomeScreen.Listener> implements HomeScreen{

    private FloatingActionButton noteAddButton;

    public HomeScreenView(LayoutInflater layoutInflater, @Nullable ViewGroup parent)
    {
        setRootView(layoutInflater.inflate(R.layout.home_screen,parent,false));
        inflateUIElements();
        initUserInteractions();
    }


    @Override
    public void inflateUIElements() {
        noteAddButton = findViewById(R.id.note_add_button);

    }

    @Override
    public void initUserInteractions()
    {
        setClickListener(noteAddButton,EventTypes.NOTE_ADD_BUTTON_CLICKED);
    }

    private void setClickListener(View view, final int EventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {
                    switch (EventType)
                    {
                        case EventTypes.NOTE_ADD_BUTTON_CLICKED:
                            listener.onNoteAddButtonClicked();
                            break;
                    }
                }

            }
        });

    }




}

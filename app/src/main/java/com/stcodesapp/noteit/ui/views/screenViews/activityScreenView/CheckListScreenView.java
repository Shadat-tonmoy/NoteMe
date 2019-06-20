package com.stcodesapp.noteit.ui.views.screenViews.activityScreenView;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.CheckListAdapter;
import com.stcodesapp.noteit.ui.views.baseScreens.BaseObservableScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.CheckListScreen;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.ManualContactScreen;

public class CheckListScreenView  extends BaseObservableScreenView<CheckListScreen.Listener> implements CheckListScreen{

    private RecyclerView checkList;
    private CheckListAdapter checkListAdapter;
    private Toolbar toolbar;
    private EditText checkListTitleField, checkListTitle2ndField;
    private FloatingActionButton doneButton;
    private RecyclerView.LayoutManager layoutManager;

    public CheckListScreenView(LayoutInflater inflater, @Nullable ViewGroup viewGroup)
    {
        setRootView(inflater.inflate(R.layout.checklist_sceen_view,viewGroup,false));
        inflateUIElements();
    }


    @Override
    public void initUserInteractions() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {
                    listener.onNavigateUp();
                }
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Listener listener:getListeners())
                {
                    listener.onCheckListDoneButtonClicked();
                }
            }
        });


    }

    @Override
    public void inflateUIElements() {

        toolbar = findViewById(R.id.app_toolbar);
        toolbar.setTitle(getContext().getResources().getString(R.string.add_check_list));
        toolbar.setNavigationIcon(getContext().getResources().getDrawable(R.drawable.back_white));
        checkList = findViewById(R.id.checklist_item_list);
        checkListAdapter = new CheckListAdapter(getContext(),false);
        layoutManager = new LinearLayoutManager(getContext());
        checkList.setLayoutManager(layoutManager);
        checkList.setAdapter(checkListAdapter);
        doneButton = findViewById(R.id.check_list_done_button);
        checkListTitleField = findViewById(R.id.check_item_title_field);

    }

    public CheckListAdapter getCheckListAdapter() {
        return checkListAdapter;
    }

    public RecyclerView getCheckList() {
        return checkList;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public EditText getCheckListTitleField() {
        return checkListTitleField;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setCheckListAdapterListener(CheckListAdapter.Listener listener)
    {
        checkListAdapter.setListener(listener);
    }
}

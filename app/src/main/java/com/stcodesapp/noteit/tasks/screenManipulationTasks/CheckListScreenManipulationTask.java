package com.stcodesapp.noteit.tasks.screenManipulationTasks;

import android.app.Activity;
import android.view.View;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.SingleCheckList;
import com.stcodesapp.noteit.monetization.ads.AdMob;
import com.stcodesapp.noteit.monetization.ads.AdNetwork;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.CheckListScreenView;

import java.util.List;

public class CheckListScreenManipulationTask {

    private Activity activity;
    private CheckListScreenView checkListScreenView;

    public CheckListScreenManipulationTask(Activity activity) {
        this.activity = activity;
    }

    public void bindView(CheckListScreenView checkListScreenView) {
        this.checkListScreenView = checkListScreenView;
    }


    public void bindCheckListObject(List<SingleCheckList> singleCheckLists) {
        if (singleCheckLists.size() > Constants.ZERO) {
            checkListScreenView.getCheckListAdapter().bindCheckListObjects(singleCheckLists);
        }
    }


    public void initToolbar() {
        checkListScreenView.initUserInteractions();
    }

}

package com.stcodesapp.noteit.controllers.activityController;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.IAPTypes;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks.IAPScreenManipulationTasks;
import com.stcodesapp.noteit.ui.views.screenViews.activityScreenView.IAPScreenView;
import com.stcodesapp.noteit.ui.views.screens.activityScreen.IAPScreen;

public class IAPActivityController implements IAPScreen.Listener {

    private Activity activity;
    private TasksFactory tasksFactory;
    private IAPScreenManipulationTasks iapScreenManipulationTasks;
    private IAPScreenView iapScreenView;
    private int IAPType = Constants.INVALID;


    public IAPActivityController(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.tasksFactory = tasksFactory;
        this.iapScreenManipulationTasks = tasksFactory.getIAPScreenManipulationTasks();
    }


    public void bindView(IAPScreenView iapScreenView) {
        this.iapScreenView = iapScreenView;
        iapScreenManipulationTasks.bindView(iapScreenView);
    }

    public void onStart()
    {
        iapScreenView.registerListener(this);
    }

    public void onStop()
    {
        iapScreenView.unregisterListener(this);
    }

    @Override
    public void onNavigateUp() {
        activity.finish();
    }

    @Override
    public void onMonthlySubsClicked() {
        iapScreenManipulationTasks.setMonthlyForeground();
        IAPType = IAPTypes.MONTHLY_SUBS;

    }

    @Override
    public void onHalfYearlySubsClicked() {
        iapScreenManipulationTasks.setHalfYearlyForeground();
        IAPType = IAPTypes.HALF_YEARLY_SUBS;

    }

    @Override
    public void onYearlySubsClicked() {

        IAPType = IAPTypes.YEARLY_SUBS;
    }

    @Override
    public void onLifetimeSubsClicked() {
        iapScreenManipulationTasks.setLifeTimeForeground();
        IAPType = IAPTypes.LIFE_TIME_PURCHASE;

    }

    @Override
    public void onDoneButtonClicked() {


    }
}

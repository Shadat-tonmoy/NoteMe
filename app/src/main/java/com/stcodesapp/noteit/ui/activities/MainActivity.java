package com.stcodesapp.noteit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.stcodesapp.noteit.controllers.activityController.MainActivityController;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;

public class MainActivity extends BaseNavigationDrawerActivity {

    private FragmentNavigationTasks fragmentNavigationTasks;
    private MainActivityController mainActivityController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        navigationDrawerView = getCompositionRoot().getViewFactory().getNavigationDrawerView(null);
//        mainActivityController = getCompositionRoot().getFragmentControllerFactory().getMainActivityController();
//        mainActivityController.bindView(navigationDrawerView);

    }


    @Override
    protected void onStart() {
        super.onStart();
//        mainActivityController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mainActivityController.onStop();
    }
}

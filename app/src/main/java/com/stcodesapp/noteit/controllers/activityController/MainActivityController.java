package com.stcodesapp.noteit.controllers.activityController;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.ui.commons.NavigationDrawerView;
import com.stcodesapp.noteit.ui.views.screens.commons.NavigationDrawerScreen;

public class MainActivityController implements NavigationDrawerScreen.Listener {

    private TasksFactory tasksFactory;
    private NavigationDrawerView navigationDrawerView;
    private FragmentNavigationTasks fragmentNavigationTasks;

    public MainActivityController(TasksFactory tasksFactory) {
        this.tasksFactory = tasksFactory;
        this.fragmentNavigationTasks = tasksFactory.getFragmentNavigationTasks();
    }

    public void bindView(NavigationDrawerView navigationDrawerView) {
        this.navigationDrawerView = navigationDrawerView;
    }

    public void onStart()
    {
        navigationDrawerView.registerListener(this);
    }

    public void onStop()
    {
        navigationDrawerView.unregisterListener(this);
    }

    @Override
    public void onNavigationDrawerItemClicked(int itemId) {
        switch (itemId)
        {
            case R.id.nav_menu_1:
                fragmentNavigationTasks.toHomeScreen();
                break;
            case R.id.nav_menu_2:
                fragmentNavigationTasks.toNavigationMenu1Fragment();
                break;


        }


    }
}

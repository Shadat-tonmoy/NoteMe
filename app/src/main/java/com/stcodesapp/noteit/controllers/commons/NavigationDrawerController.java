package com.stcodesapp.noteit.controllers.commons;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.ui.commons.NavigationDrawerView;
import com.stcodesapp.noteit.ui.views.screens.commons.NavigationDrawerScreen;

public class NavigationDrawerController implements NavigationDrawerScreen.Listener {

    private final FragmentNavigationTasks fragmentNavigationTasks;
    private NavigationDrawerView navigationDrawerView;

    public NavigationDrawerController(FragmentNavigationTasks fragmentNavigationTasks) {
        this.fragmentNavigationTasks = fragmentNavigationTasks;
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
            case R.id.home_menu:
                fragmentNavigationTasks.toHomeScreen();
                updateToolbarTitle(FragmentTags.HOME_SCREEN);
                break;
            case R.id.important_menu:
                fragmentNavigationTasks.toImportantNoteFragment();
                updateToolbarTitle(FragmentTags.IMPORTANT_NOTES);
                break;
            case R.id.contact_menu:
                fragmentNavigationTasks.toContactFragment();
                updateToolbarTitle(FragmentTags.CONTACTS);
                break;
            case R.id.email_menu:
                fragmentNavigationTasks.toEmailFragment();
                updateToolbarTitle(FragmentTags.EMAILS);
                break;
        }
    }

    private void updateToolbarTitle(String title)
    {
        navigationDrawerView.getToolbar().setTitle(title);
        switch (title)
        {
            case FragmentTags.HOME_SCREEN:
                navigationDrawerView.checkMenuItem(R.id.home_menu);
                break;
            case FragmentTags.IMPORTANT_NOTES:
                navigationDrawerView.checkMenuItem(R.id.important_menu);
                break;
            case FragmentTags.CONTACTS:
                navigationDrawerView.checkMenuItem(R.id.contact_menu);
                break;
        }
    }


    public void onBackPressed() {
        updateToolbarTitle(fragmentNavigationTasks.getCurrentFragmentTag());
    }

    public void onPostCreate() {
        fragmentNavigationTasks.toHomeScreen();
        updateToolbarTitle(FragmentTags.HOME_SCREEN);
    }
}

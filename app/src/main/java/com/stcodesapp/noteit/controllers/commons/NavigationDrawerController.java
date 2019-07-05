package com.stcodesapp.noteit.controllers.commons;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.tasks.functionalTasks.IAPBillingTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.SharingTasks;
import com.stcodesapp.noteit.ui.commons.NavigationDrawerView;
import com.stcodesapp.noteit.ui.views.screens.commons.NavigationDrawerScreen;

public class NavigationDrawerController implements NavigationDrawerScreen.Listener {

    private final FragmentNavigationTasks fragmentNavigationTasks;
    private final ActivityNavigationTasks activityNavigationTasks;
    private TasksFactory tasksFactory;
    private NavigationDrawerView navigationDrawerView;
    private SharingTasks sharingTasks;
    private IAPBillingTasks iapBillingTasks;


    public NavigationDrawerController(TasksFactory tasksFactory) {
        this.tasksFactory= tasksFactory;
        this.fragmentNavigationTasks = tasksFactory.getFragmentNavigationTasks();
        this.activityNavigationTasks = tasksFactory.getActivityNavigationTasks();
        this.sharingTasks = tasksFactory.getSharingTasks();
        this.iapBillingTasks = tasksFactory.getIAPBillingTasks();
    }

    public void bindView(NavigationDrawerView navigationDrawerView) {
        this.navigationDrawerView = navigationDrawerView;
    }

    public void onStart()
    {
        navigationDrawerView.registerListener(this);
        iapBillingTasks.setupBillingClient();
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
            case R.id.nav_pro_version:
                activityNavigationTasks.toProVersionScreen();
                break;
            case R.id.email_menu:
                fragmentNavigationTasks.toEmailFragment();
                updateToolbarTitle(FragmentTags.EMAILS);
                break;
            case R.id.check_list_menu:
                fragmentNavigationTasks.toCheckListFragment();
                updateToolbarTitle(FragmentTags.CHECKLIST);
                break;
            case R.id.nav_share:
                sharingTasks.shareApp();
                break;
            case R.id.nav_email_support:
                sharingTasks.emailSupport();
                break;
            case R.id.nav_rate:
                sharingTasks.rateApp();
                break;
            case R.id.nav_privacy_policy:
                activityNavigationTasks.toPrivacyPolicyScreen();
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

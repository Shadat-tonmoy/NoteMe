package com.stcodesapp.noteit.tasks.navigationTasks;
import com.stcodesapp.noteit.common.FragmentFrameHelper;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.ui.fragments.HomeScreenFragment;
import com.stcodesapp.noteit.ui.fragments.ImportantNoteFragment;
import com.stcodesapp.noteit.ui.fragments.NavigationMenu2Fragment;

public class FragmentNavigationTasks {

    private FragmentFrameHelper fragmentFrameHelper;

    public FragmentNavigationTasks(FragmentFrameHelper fragmentFrameHelper) {
        this.fragmentFrameHelper= fragmentFrameHelper;

    }


    public void toHomeScreen()
    {
        fragmentFrameHelper.replaceFragmentAndClearBackstack(HomeScreenFragment.newInstance(),FragmentTags.HOME_SCREEN);
    }


    public void toNavigationMenu1Fragment()
    {
        fragmentFrameHelper.replaceFragment(ImportantNoteFragment.newInstance(), FragmentTags.IMPORTANT_NOTES);

    }

    public void toNavigationMenu2Fragment()
    {
        fragmentFrameHelper.replaceFragment(NavigationMenu2Fragment.newInstance(), FragmentTags.NAVIGATION_MENU_2);

    }

    public String getCurrentFragmentTag()
    {
        return fragmentFrameHelper.getCurrentFragment().getTag();
    }
}

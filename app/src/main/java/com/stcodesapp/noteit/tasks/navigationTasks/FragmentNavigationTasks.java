package com.stcodesapp.noteit.tasks.navigationTasks;
import com.stcodesapp.noteit.common.FragmentFrameHelper;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.ui.fragments.HomeScreenFragment;
import com.stcodesapp.noteit.ui.fragments.ImportantNoteFragment;
import com.stcodesapp.noteit.ui.fragments.ContactsFragment;

public class FragmentNavigationTasks {

    private FragmentFrameHelper fragmentFrameHelper;

    public FragmentNavigationTasks(FragmentFrameHelper fragmentFrameHelper) {
        this.fragmentFrameHelper= fragmentFrameHelper;

    }


    public void toHomeScreen()
    {
        fragmentFrameHelper.replaceFragmentAndClearBackstack(HomeScreenFragment.newInstance(),FragmentTags.HOME_SCREEN);
    }


    public void toImportantNoteFragment()
    {
        fragmentFrameHelper.replaceFragment(ImportantNoteFragment.newInstance(), FragmentTags.IMPORTANT_NOTES);

    }

    public void toContactFragment()
    {
        fragmentFrameHelper.replaceFragment(ContactsFragment.newInstance(), FragmentTags.CONTACTS);

    }

    public String getCurrentFragmentTag()
    {
        return fragmentFrameHelper.getCurrentFragment().getTag();
    }
}

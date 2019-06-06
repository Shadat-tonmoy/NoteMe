package com.stcodesapp.noteit.common;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.stcodesapp.noteit.controllers.commons.FragmentFrameWrapper;

public class FragmentFrameHelper {


    private final Activity mActivity;
    private final FragmentFrameWrapper mFragmentFrameWrapper;
    private final FragmentManager mFragmentManager;

    public FragmentFrameHelper(Activity activity, FragmentFrameWrapper fragmentFrameWrapper, FragmentManager fragmentManager) {
        mActivity = activity;
        mFragmentFrameWrapper = fragmentFrameWrapper;
        mFragmentManager = fragmentManager;
    }

    public void replaceFragment(Fragment newFragment,String tag) {
        replaceFragment(newFragment, true, false,tag);
    }

    /*public void replaceFragmentDontAddToBackstack(Fragment newFragment) {
        replaceFragment(newFragment, false, false);
    }*/

    public void replaceFragmentAndClearBackstack(Fragment newFragment,String tag) {
        replaceFragment(newFragment, false, true,tag);
    }

    public void navigateUp() {

        // Some navigateUp calls can be "lost" if they happen after the state has been saved
        if (mFragmentManager.isStateSaved()) {
            return;
        }

        Fragment currentFragment = getCurrentFragment();

        if (mFragmentManager.getBackStackEntryCount() > 0) {

            // In a normal world, just popping back stack would be sufficient, but since android
            // is not normal, a call to popBackStack can leave the popped fragment on screen.
            // Therefore, we start with manual removal of the current fragment.
            // Description of the issue can be found here: https://stackoverflow.com/q/45278497/2463035
            removeCurrentFragment();

            if (mFragmentManager.popBackStackImmediate()) {
                return; // navigated "up" in fragments back-stack
            }
        }

       /* if (HierarchicalFragment.class.isInstance(currentFragment)) {
            Fragment parentFragment =
                    ((HierarchicalFragment)currentFragment).getHierarchicalParentFragment();
            if (parentFragment != null) {
                replaceFragment(parentFragment, false, true);
                return; // navigate "up" to hierarchical parent fragment
            }
        }

        if (mActivity.onNavigateUp()) {
            return; // navigated "up" to hierarchical parent activity
        }*/

        mActivity.onBackPressed(); // no "up" navigation targets - just treat UP as back press
    }

    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(getFragmentFrameId());
    }

    private void replaceFragment(Fragment newFragment, boolean addToBackStack, boolean clearBackStack,String tag) {
        if(getCurrentFragment()!=null && tag.equals(getCurrentFragment().getTag()))
            return;
        if (clearBackStack) {
            if (mFragmentManager.isStateSaved()) {
                // If the state is saved we can't clear the back stack. Simply not doing this, but
                // still replacing fragment is a bad idea. Therefore we abort the entire operation.
                return;
            }
            // Remove all entries from back stack
            mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (addToBackStack)
            fragmentTransaction.addToBackStack(tag);

        // Change to a new fragment
        fragmentTransaction.replace(getFragmentFrameId(), newFragment, tag);

        if (mFragmentManager.isStateSaved()) {
            // We acknowledge the possibility of losing this transaction if the app undergoes
            // save&restore flow after it is committed.
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            fragmentTransaction.commit();
        }
    }

    private void removeCurrentFragment() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.remove(getCurrentFragment());
        ft.commit();

        // not sure it is needed; will keep it as a reminder to myself if there will be problems
        // mFragmentManager.executePendingTransactions();
    }

    private int getFragmentFrameId() {
        return mFragmentFrameWrapper.getFragmentFrame().getId();
    }
}

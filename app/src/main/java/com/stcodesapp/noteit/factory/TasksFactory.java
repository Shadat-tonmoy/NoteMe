package com.stcodesapp.noteit.factory;

import android.support.v4.app.FragmentActivity;
import com.stcodesapp.noteit.common.FragmentFrameHelper;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.ManualContactScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.ManualEmailScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.NoteFieldScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.ClipboardTasks;
import com.stcodesapp.noteit.tasks.utilityTasks.SharingTasks;

public class TasksFactory {

    private FragmentActivity activity;
    private FragmentFrameHelper fragmentFrameHelper;

    public TasksFactory(FragmentActivity activity, FragmentFrameHelper fragmentFrameHelper) {
        this.activity = activity;
        this.fragmentFrameHelper= fragmentFrameHelper;
    }

    public TasksFactory(FragmentActivity activity) {
        this.activity = activity;

    }

    public ActivityNavigationTasks getActivityNavigationTasks()
    {
        return new ActivityNavigationTasks(activity);
    }

    public FragmentNavigationTasks getFragmentNavigationTasks()
    {
        return new FragmentNavigationTasks(fragmentFrameHelper);
    }

    public NoteFieldScreenManipulationTasks getNoteFieldScreenManipulationTasks()
    {
        return new NoteFieldScreenManipulationTasks(activity,getListenerFactory(), getUiComponentFactory());
    }


    public ManualContactScreenManipulationTasks getManualContactScreenManipulationTasks()
    {
        return new ManualContactScreenManipulationTasks(activity);
    }

    public ManualEmailScreenManipulationTasks getManualEmailScreenManipulationTasks()
    {
        return new ManualEmailScreenManipulationTasks(activity);
    }

    public AppPermissionTrackingTasks getAppPermissionTrackingTasks()
    {
        return new AppPermissionTrackingTasks(activity);
    }

    public FileIOTasks getFileIOTasks()
    {
        return new FileIOTasks(activity);
    }

    public ListenerFactory getListenerFactory()
    {
        return new ListenerFactory(activity);
    }

    private UIComponentFatory getUiComponentFactory()
    {
        return new UIComponentFatory();
    }

    public ClipboardTasks getClipboardTasks()
    {
        return new ClipboardTasks(activity);
    }

    public SharingTasks getSharingTasks()
    {
        return new SharingTasks(activity);
    }



}

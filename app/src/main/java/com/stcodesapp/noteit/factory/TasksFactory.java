package com.stcodesapp.noteit.factory;

import android.support.v4.app.FragmentActivity;
import com.stcodesapp.noteit.common.FragmentFrameHelper;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.NoteFieldValidationTask;
import com.stcodesapp.noteit.tasks.functionalTasks.PDFCreationTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.VoiceInputTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.CheckListScreenManipulationTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.ContactScreenManipulationTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.EmailScreenManipulationTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.HomeScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.ManualContactScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.ManualEmailScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.NoteFieldScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.SortingDialogManipulationTask;
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
        return new NoteFieldScreenManipulationTasks(activity,getListeningTasks(), getUiComponentFactory());
    }


    public ManualContactScreenManipulationTasks getManualContactScreenManipulationTasks()
    {
        return new ManualContactScreenManipulationTasks(activity);
    }

    public ManualEmailScreenManipulationTasks getManualEmailScreenManipulationTasks()
    {
        return new ManualEmailScreenManipulationTasks(activity);
    }

    public HomeScreenManipulationTasks getHomeScreenManipulationTasks()
    {
        return new HomeScreenManipulationTasks(activity);
    }

    public AppPermissionTrackingTasks getAppPermissionTrackingTasks()
    {
        return new AppPermissionTrackingTasks(activity);
    }

    public FileIOTasks getFileIOTasks()
    {
        return new FileIOTasks(activity);
    }

    public ListeningTasks getListeningTasks()
    {
        return new ListeningTasks(activity,getDatabaseTasks());
    }

    private UIComponentFatory getUiComponentFactory()
    {
        return new UIComponentFatory();
    }

    public ClipboardTasks getClipboardTasks()
    {
        return new ClipboardTasks(activity);
    }

    public VoiceInputTasks getVoiceInputTasks()
    {
        return new VoiceInputTasks(activity);
    }

    public SharingTasks getSharingTasks()
    {
        return new SharingTasks(activity);
    }

    public DatabaseTasks getDatabaseTasks()
    {
        return new DatabaseTasks(activity);
    }

    public NoteFieldValidationTask getNoteFieldValidationTask(NoteComponents noteComponents)
    {
        return new NoteFieldValidationTask(activity,noteComponents);
    }

    public SortingDialogManipulationTask getSortingDialogManipulationTask()
    {
        return new SortingDialogManipulationTask(activity);
    }

    public EmailScreenManipulationTask getEmailScreenManipulationTask()
    {
        return new EmailScreenManipulationTask(activity);
    }

    public ContactScreenManipulationTask getContactScreenManipulationTask()
    {
        return new ContactScreenManipulationTask(activity);
    }

    public CheckListScreenManipulationTask getCheckListScreenManipulationTask()
    {
        return new CheckListScreenManipulationTask(activity,getClipboardTasks());
    }

    public PDFCreationTasks getPDFCreationTasks()
    {
        return new PDFCreationTasks(activity,this);
    }



}

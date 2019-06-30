package com.stcodesapp.noteit.factory;

import android.support.v4.app.FragmentActivity;
import com.stcodesapp.noteit.common.FragmentFrameHelper;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllAudioSelectionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllImageSelectionTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.DialogManagementTask;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.AdStrategyTrackingTask;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.IAPTrackingTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileDeletingTask;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileIOTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.fileRelatedTasks.FileMovingTask;
import com.stcodesapp.noteit.tasks.functionalTasks.phoneFunctionAccessTasks.ImageCapturingTask;
import com.stcodesapp.noteit.tasks.functionalTasks.NoteFieldValidationTask;
import com.stcodesapp.noteit.tasks.functionalTasks.PDFCreationTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.phoneFunctionAccessTasks.VoiceInputTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.phoneFunctionAccessTasks.VoiceRecordTask;
import com.stcodesapp.noteit.tasks.navigationTasks.ActivityNavigationTasks;
import com.stcodesapp.noteit.tasks.navigationTasks.FragmentNavigationTasks;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.RateUSPopupTrackingTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks.CheckListActivityScreenManipulationTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.CheckListScreenManipulationTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.ContactScreenManipulationTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.EmailScreenManipulationTask;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.FileSaveScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.HomeScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks.ManualContactScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks.ManualEmailScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.activityScreenManipulationTasks.NoteFieldScreenManipulationTasks;
import com.stcodesapp.noteit.tasks.screenManipulationTasks.fragmentScreenManipulationTass.SortingDialogManipulationTask;
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
        return new NoteFieldScreenManipulationTasks(activity,getListeningTasks(), getUiComponentFactory(),this);
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
        return new ListeningTasks(activity,getDatabaseTasks(),this);
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

    public CheckListActivityScreenManipulationTask getCheckListActivityScreenManipulationTask()
    {
        return new CheckListActivityScreenManipulationTask(activity,getClipboardTasks());
    }

    public CheckListScreenManipulationTask getCheckListScreenManipulationTask()
    {
        return new CheckListScreenManipulationTask(activity);
    }

    public PDFCreationTasks getPDFCreationTasks()
    {
        return new PDFCreationTasks(activity,this);
    }


    public VoiceRecordTask getVoiceRecordTask()
    {
        return new VoiceRecordTask(activity);
    }


    public FileSaveScreenManipulationTasks getFileSaveScreenManipulationTasks() {
        return new FileSaveScreenManipulationTasks(activity);
    }

    public FileMovingTask getFileMovingTask(FileMovingTask.Listener listener) {
        FileMovingTask fileMovingTask = new FileMovingTask();
        fileMovingTask.setListener(listener);
        return fileMovingTask;

    }

    public FileDeletingTask getFileDeletingTask(FileDeletingTask.Listener listener) {
        FileDeletingTask fileDeletingTask = new FileDeletingTask();
        fileDeletingTask.setListener(listener);
        return fileDeletingTask;

    }

    public ImageCapturingTask getImageCapturingTask()
    {
        return new ImageCapturingTask(activity,this);
    }

    public RateUSPopupTrackingTasks getRateUSPopupTrackingTasks()
    {
        return new RateUSPopupTrackingTasks(activity);
    }

    public DialogManagementTask getDialogManagementTask()
    {
        return new DialogManagementTask(activity,this);
    }

    public AllImageSelectionTasks getAllImageSelectionTasks(AllImageSelectionTasks.Listener listener,int purpose)
    {
        return new AllImageSelectionTasks(activity,listener, purpose);
    }

    public AllAudioSelectionTasks getAllAudioSelectionTasks(AllAudioSelectionTasks.Listener listener, int purpose)
    {
        return new AllAudioSelectionTasks(activity,listener, purpose);
    }

    public AdStrategyTrackingTask getAdStrategyTrackingTask()
    {
        return new AdStrategyTrackingTask(activity);
    }

    public IAPTrackingTasks getIAPTrackingTasks()
    {
        return new IAPTrackingTasks(activity);
    }
}

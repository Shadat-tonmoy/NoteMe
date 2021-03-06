package com.stcodesapp.noteit.tasks.databaseTasks;

import android.content.Context;

import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.allDeletionTask.AllDeletionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.AudioDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.CheckListDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.CheckListItemDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.ContactDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.EmailDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.ImageDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask.NoteDeleteTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.AudioInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.CheckListInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.CheckListItemInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ContactInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.EmailInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.ImageInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.insertionTasks.NoteInsertTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllCheckListSelectionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllContactSelectionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AllEmailSelectionTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.AudioSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.CheckListItemSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.CheckListSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ContactSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.EmailSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ImageSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ImportantNoteSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteComponentSelectionTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.NoteSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.updateTasks.CheckListItemUpdateTask;
import com.stcodesapp.noteit.tasks.databaseTasks.updateTasks.CheckListUpdateTask;

public class DatabaseTasks {

    private final Context context;

    public DatabaseTasks(Context context) {
        this.context = context;
    }

    /**
    * List of insertion tasks
    * */

    public NoteInsertTask getNoteInsertTask(NoteInsertTask.Listener listener)
    {
        NoteInsertTask noteInsertTask = new NoteInsertTask(context);
        noteInsertTask.setListener(listener);
        return noteInsertTask;
    }

    public EmailInsertTask getEmailInsertTask(EmailInsertTask.Listener listener)
    {
        EmailInsertTask emailInsertTask = new EmailInsertTask(context);
        emailInsertTask .setListener(listener);
        return emailInsertTask ;
    }

    public ContactInsertTask getContactInsertTask(ContactInsertTask.Listener listener)
    {
        ContactInsertTask contactInsertTask = new ContactInsertTask(context);
        contactInsertTask.setListener(listener);
        return contactInsertTask;
    }

    public AudioInsertTask getAudioInsertTask(AudioInsertTask.Listener listener)
    {
        AudioInsertTask audioInsertTask = new AudioInsertTask(context);
        audioInsertTask.setListener(listener);
        return audioInsertTask;
    }

    public ImageInsertTask getImageInsertTask(ImageInsertTask.Listener listener)
    {
        ImageInsertTask imageInsertTask = new ImageInsertTask(context);
        imageInsertTask.setListener(listener);
        return imageInsertTask;
    }

    public CheckListInsertTask getCheckListInsertTask(CheckListInsertTask.Listener listener)
    {
        CheckListInsertTask checkListInsertTask = new CheckListInsertTask(context);
        checkListInsertTask.setListener(listener);
        return checkListInsertTask;
    }

    public CheckListItemInsertTask getCheckListItemInsertTask(CheckListItemInsertTask.Listener listener)
    {
        CheckListItemInsertTask checkListItemInsertTask = new CheckListItemInsertTask(context);
        checkListItemInsertTask.setListener(listener);
        return checkListItemInsertTask;
    }


    /**
     * List of selection tasks
     * */
    public NoteSelectTask getNoteSelectTask(NoteSelectTask.Listener listener)
    {
        NoteSelectTask noteSelectTask = new NoteSelectTask(context);
        noteSelectTask.setListener(listener);
        return noteSelectTask;
    }

    public ImportantNoteSelectTask getImportantNoteSelectTask(ImportantNoteSelectTask.Listener listener)
    {
        ImportantNoteSelectTask importantNoteSelectTask = new ImportantNoteSelectTask(context);
        importantNoteSelectTask.setListener(listener);
        return importantNoteSelectTask;
    }

    public EmailSelectTask getEmailSelectTask(EmailSelectTask.Listener listener)
    {
        EmailSelectTask emailSelectTask = new EmailSelectTask(context, ComponentType.EMAIL);
        emailSelectTask.setListener(listener);
        return emailSelectTask;
    }

    public ContactSelectTask getContactSelectTask(ContactSelectTask.Listener listener)
    {
        ContactSelectTask ContactSelectTask = new ContactSelectTask(context, ComponentType.CONTACT);
        ContactSelectTask.setListener(listener);
        return ContactSelectTask;
    }

    public AudioSelectTask getAudioSelectTask(AudioSelectTask.Listener listener)
    {
        AudioSelectTask AudioSelectTask = new AudioSelectTask(context, ComponentType.AUDIO);
        AudioSelectTask.setListener(listener);
        return AudioSelectTask;
    }

    public ImageSelectTask getImageSelectTask(ImageSelectTask.Listener listener)
    {
        ImageSelectTask ImageSelectTask = new ImageSelectTask(context, ComponentType.IMAGE);
        ImageSelectTask.setListener(listener);
        return ImageSelectTask;
    }

    public CheckListSelectTask getCheckListSelectTask(CheckListSelectTask.Listener listener)
    {
        CheckListSelectTask checkListSelectTask = new CheckListSelectTask(context,ComponentType.CHECKLIST);
        checkListSelectTask.setListener(listener);
        return checkListSelectTask;
    }

    public CheckListItemSelectTask getCheckListItemSelectTask(CheckListItemSelectTask.Listener listener)
    {
        CheckListItemSelectTask checkListItemSelectTask = new CheckListItemSelectTask(context,ComponentType.CHECKLIST_ITEM);
        checkListItemSelectTask.setListener(listener);
        return checkListItemSelectTask;
    }

    public AllContactSelectionTasks getAllContactSelectionTasks(AllContactSelectionTasks.Listener listener)
    {
        AllContactSelectionTasks allContactSelectionTasks = new AllContactSelectionTasks(context,listener);
        return allContactSelectionTasks;
    }

    public AllEmailSelectionTasks getAllEmailSelectionTasks(AllEmailSelectionTasks.Listener listener)
    {
        AllEmailSelectionTasks allEmailSelectionTasks = new AllEmailSelectionTasks(context,listener);
        return allEmailSelectionTasks;
    }

    public AllCheckListSelectionTasks getAllCheckListSelectionTasks(AllCheckListSelectionTasks.Listener listener)
    {
        AllCheckListSelectionTasks allCheckListSelectionTasks = new AllCheckListSelectionTasks(context,listener);
        return allCheckListSelectionTasks;
    }


    /**
     * List of deletion tasks
     * */

    public EmailDeleteTask getEmailDeleteTask(EmailDeleteTask.Listener listener)
    {
        EmailDeleteTask emailDeleteTask = new EmailDeleteTask(context, ComponentType.EMAIL);
        emailDeleteTask.setListener(listener);
        return emailDeleteTask;
    }


    public ContactDeleteTask getContactDeleteTask(ContactDeleteTask.Listener listener)
    {
        ContactDeleteTask contactDeleteTask = new ContactDeleteTask(context, ComponentType.CONTACT);
        contactDeleteTask.setListener(listener);
        return contactDeleteTask;
    }

    public AudioDeleteTask getAudioDeleteTask(AudioDeleteTask.Listener listener)
    {
        AudioDeleteTask audioDeleteTask = new AudioDeleteTask(context, ComponentType.AUDIO);
        audioDeleteTask.setListener(listener);
        return audioDeleteTask;
    }

    public ImageDeleteTask getImageDeleteTask(ImageDeleteTask.Listener listener)
    {
        ImageDeleteTask imageDeleteTask = new ImageDeleteTask(context, ComponentType.IMAGE);
        imageDeleteTask.setListener(listener);
        return imageDeleteTask;
    }


    public CheckListDeleteTask getCheckListDeleteTask(CheckListDeleteTask.Listener listener)
    {
        CheckListDeleteTask checkListDeleteTask= new CheckListDeleteTask(context, ComponentType.CHECKLIST);
        checkListDeleteTask.setListener(listener);
        return checkListDeleteTask;
    }

    public CheckListItemDeleteTask getCheckListItemDeleteTask(CheckListItemDeleteTask.Listener listener)
    {
        CheckListItemDeleteTask checkListItemDeleteTask = new CheckListItemDeleteTask(context, ComponentType.CHECKLIST_ITEM);
        checkListItemDeleteTask.setListener(listener);
        return checkListItemDeleteTask ;
    }

    public NoteDeleteTask getNoteDeleteTask(NoteDeleteTask.Listener listener)
    {
        NoteDeleteTask noteDeleteTask= new NoteDeleteTask(context, ComponentType.NOTE);
        noteDeleteTask.setListener(listener);
        return noteDeleteTask;
    }

    public NoteComponentSelectionTask getNoteComponentSelectionTask(NoteComponentSelectionTask.Listener listener,NoteComponents noteComponents)
    {
        NoteComponentSelectionTask noteComponentSelectionTask = new NoteComponentSelectionTask(context,noteComponents);
        noteComponentSelectionTask.setListener(listener);
        return noteComponentSelectionTask;

    }

    public AllDeletionTasks getAllDeletionTasks(AllDeletionTasks.Listener listener, ComponentType componentType)
    {
        AllDeletionTasks allDeletionTasks = new AllDeletionTasks(context,componentType);
        allDeletionTasks.setListener(listener);
        return allDeletionTasks;
    }


    /**
     * list of update tasks
     */

    public NoteUpdateTask getNoteUpdateTask()
    {
        NoteUpdateTask noteUpdateTask = new NoteUpdateTask(context);
        return noteUpdateTask;
    }

    public CheckListUpdateTask getCheckListUpdateTask()
    {
        CheckListUpdateTask checkListUpdateTask= new CheckListUpdateTask(context);
        return checkListUpdateTask;
    }

    public CheckListItemUpdateTask getCheckListItemUpdateTask()
    {
        CheckListItemUpdateTask checkListItemUpdateTask = new CheckListItemUpdateTask(context);
        return checkListItemUpdateTask;
    }



}

package com.stcodesapp.noteit.tasks.databaseTasks.deletionTasks.singleDeletionTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.database.NoteDatabase;
import com.stcodesapp.noteit.models.Audio;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Image;
import com.stcodesapp.noteit.models.Note;

import java.util.List;

public class BaseDeletionTasks<Component> extends AsyncTask<Component, Void, Component>{



    private Context context;
    private NoteDatabase noteDatabase;
    private ComponentType componentType;

    public BaseDeletionTasks(Context context, ComponentType componentType)
    {
        this.context = context;
        noteDatabase = NoteDatabase.getInstance(context);
        this.componentType = componentType;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Component doInBackground(Component... components) {
        getDeletionTask(components[0]);
        return components[0];
    }

    @Override
    protected void onPostExecute(Component component) {
        super.onPostExecute(component);
        Toast.makeText(context,context.getResources().getString(R.string.note_component_deleted),Toast.LENGTH_SHORT).show();
    }

    private void getDeletionTask(Component component)
    {
        switch (componentType)
        {
            case AUDIO:
//                Log.e("WillDelete",((Audio)component).toString());
                noteDatabase.audioDao().deleteAudio((Audio)component);
                break;
            case IMAGE:
                noteDatabase.imageDao().deleteImage((Image)component);
                break;
            case EMAIL:
                noteDatabase.emailDao().deleteEmail((Email) component);
                break;
            case CONTACT:
                noteDatabase.contactDao().deleteContact((Contact)component);
                break;
            case CHECKLIST:
                noteDatabase.checkListDao().deleteCheckList((CheckList) component);
                break;
            case CHECKLIST_ITEM:
                noteDatabase.checkListItemDao().deleteCheckListItem((ChecklistItem) component);
                break;
            case NOTE:
                noteDatabase.notesDao().delete((Note)component);
                break;
            default:
                return;
        }
    }
}

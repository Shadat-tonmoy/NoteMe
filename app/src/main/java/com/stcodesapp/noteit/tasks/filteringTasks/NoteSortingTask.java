package com.stcodesapp.noteit.tasks.filteringTasks;

import android.widget.Toast;

import com.stcodesapp.noteit.constants.SortingType;
import com.stcodesapp.noteit.models.Note;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoteSortingTask {

    public interface Listener{
        void onSortingFinished(List<Note> sortedNotes);
    }

    private List<Note> notes;
    private Listener listener;

    public NoteSortingTask(List<Note> notes,Listener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    public void sortNotes(final SortingType sortingType, final boolean ascending)
    {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                switch (sortingType)
                {
                    case NOTE_TITLE:
                        if(ascending)
                            return note1.getNoteTitle().compareTo(note2.getNoteTitle());
                        else return -note1.getNoteTitle().compareTo(note2.getNoteTitle());
                    case NOTE_TEXT:
                        if(ascending)
                            return note1.getNoteText().compareTo(note2.getNoteText());
                        else return -note1.getNoteText().compareTo(note2.getNoteText());
                    case NOTE_TIME:
                        if(ascending)
                            return Long.compare(note1.getCreationTime(),note2.getCreationTime());
                        else return -Long.compare(note1.getCreationTime(),note2.getCreationTime());
                    case NOTE_IMPORTANT:
                        if(ascending)
                            return Boolean.compare(note1.isImportant(),note2.isImportant());
                        else return -Boolean.compare(note1.isImportant(),note2.isImportant());
                }
                return 0;
            }
        });
        listener.onSortingFinished(notes);
    }


}

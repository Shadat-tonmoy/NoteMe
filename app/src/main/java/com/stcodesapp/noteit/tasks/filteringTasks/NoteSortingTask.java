package com.stcodesapp.noteit.tasks.filteringTasks;

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

    public void sortNotes(final SortingType sortingType, boolean ascending)
    {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                switch (sortingType)
                {
                    case NOTE_TITLE:
                        return note1.getNoteTitle().compareTo(note2.getNoteTitle());
                    case NOTE_TEXT:
                        return note1.getNoteTitle().compareTo(note2.getNoteTitle());
                }
                return 0;
            }
        });
        listener.onSortingFinished(notes);
    }


}

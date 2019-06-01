package com.stcodesapp.noteit.tasks.filteringTasks;

import android.widget.Filter;
import android.widget.Filterable;

import com.stcodesapp.noteit.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteFilteringTask implements Filterable {

    public interface Listener{
        void onNoteFiltered(List<Note> filteredNotes);
    }

    private List<Note> filteredNotes,notes;
    private Listener listener;

    public NoteFilteringTask(List<Note> filteredNotes, List<Note> notes) {
        this.filteredNotes = filteredNotes;
        this.notes = notes;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String text = constraint.toString();
                if(text.isEmpty())
                {
                    filteredNotes = notes;
                }
                else
                {
                    List<Note> filteredNoteList = new ArrayList<>();
                    for(Note note:notes)
                    {
                        if(note.getNoteTitle().toLowerCase().contains(text.toLowerCase()))
                            filteredNoteList.add(note);
                    }
                    filteredNotes = filteredNoteList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredNotes;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredNotes = (List<Note>) results.values;
                listener.onNoteFiltered(filteredNotes);

            }
        };
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}


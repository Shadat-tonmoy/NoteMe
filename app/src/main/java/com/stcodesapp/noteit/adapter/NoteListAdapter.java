package com.stcodesapp.noteit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.UtilityTasks;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {


    public interface Listener {
        void onEditNoteClicked(Note note);
        void onDeleteNoteClicked(Note note);
        void onMoreClicked(Note note);
        void onNoteClicked(Note note);
    }


    private List<Note> notes;
    private Context context;
    private Listener listener;
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView noteHeader,noteText,noteTime,editNote,deleteNote,moreOption;
        ConstraintLayout noteRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteHeader = itemView.findViewById(R.id.note_header);
            noteText = itemView.findViewById(R.id.note_text);
            noteTime = itemView.findViewById(R.id.note_time);
            editNote = itemView.findViewById(R.id.edit_button);
            deleteNote = itemView.findViewById(R.id.delete_button);
            moreOption = itemView.findViewById(R.id.more_button);
            noteRow = itemView.findViewById(R.id.note_row);
        }
    }

    public NoteListAdapter(Context context,Listener listener)
    {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_single_row,viewGroup,false);
        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder viewHolder, int i) {
        Note note = notes.get(i);
        viewHolder.noteHeader.setText(note.getNoteTitle());
        viewHolder.noteText.setText(note.getNoteText());
        viewHolder.noteTime.setText(UtilityTasks.getHumanReadableTime(note.getCreationTime()));
        setNoteBackgroundColor(viewHolder.noteRow, note.getBackgroundColor());
        setClickListener(note,viewHolder.noteRow, viewHolder.editNote, viewHolder.deleteNote, viewHolder.moreOption);

    }

    @Override
    public int getItemCount() {
        if(notes!=null)
            return notes.size();
        return 0;
    }

    private void setNoteBackgroundColor(View view,String colorName)
    {
        if(!colorName.equals(BackgroundColors.WHITE))
        {
            view.setBackgroundColor(context.getResources().getColor(BackgroundColors.getColorMaps().get(colorName)));
            view.getBackground().setAlpha(Constants.BACKGROUND_OPACITY);

        }

    }

    public void bindNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }


    private void setClickListener(final Note note, View... views)
    {

        views[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteClicked(note);
            }
        });

        views[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditNoteClicked(note);
            }
        });

        views[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteNoteClicked(note);
            }
        });

        views[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMoreClicked(note);
            }
        });


    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}

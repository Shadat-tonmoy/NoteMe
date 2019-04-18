package com.stcodesapp.noteit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.entities.Note;
import com.stcodesapp.noteit.tasks.UtilityTasks;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {


    public interface Listener {
        void onEditNoteClick(Note note);
        void onDeleteNoteClick(Note note);
        void onMoreClick(Note note);
    }


    private List<Note> notes;
    private Context context;
    private Listener listener;
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView noteHeader,noteText,noteTime,editNote,deleteNote,moreOption;
        ImageView backgroundImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteHeader = itemView.findViewById(R.id.note_header);
            noteText = itemView.findViewById(R.id.note_text);
            noteTime = itemView.findViewById(R.id.note_time);
            editNote = itemView.findViewById(R.id.edit_button);
            deleteNote = itemView.findViewById(R.id.delete_button);
            moreOption = itemView.findViewById(R.id.more_button);
            backgroundImage = itemView.findViewById(R.id.note_header_image);
        }
    }

    public NoteListAdapter(Context context)
    {
        this.context = context;
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
        setRandomImage(viewHolder.backgroundImage);
        setClickListener(viewHolder.editNote,viewHolder.deleteNote,viewHolder.moreOption,note);
    }

    @Override
    public int getItemCount() {
        if(notes!=null)
            return notes.size();
        return 0;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    private void setRandomImage(ImageView view)
    {
        String randomImage = UtilityTasks.getRandomImage();
        try {
            Glide.with(context).load(context.getResources()
                    .getIdentifier(randomImage, "drawable", context.getPackageName())).thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setClickListener(View edit, View delete, View more, final Note note)
    {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditNoteClick(note);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteNoteClick(note);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMoreClick(note);
            }
        });


    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}

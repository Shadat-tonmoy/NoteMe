package com.stcodesapp.noteit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;
import com.stcodesapp.noteit.tasks.filteringTasks.NoteFilteringTask;
import com.stcodesapp.noteit.tasks.filteringTasks.NoteSortingTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> implements NoteFilteringTask.Listener,NoteSortingTask.Listener {


    @Override
    public void onNoteFiltered(List<Note> filteredNotes) {
        bindFilteredNotes(filteredNotes);
    }

    @Override
    public void onSortingFinished(List<Note> sortedNotes) {
        bindSortedNotes(sortedNotes);
        Toast.makeText(context,context.getResources().getString(R.string.notes_are_sorted),Toast.LENGTH_SHORT).show();

    }


    public interface Listener {
        void onContactBadgeClicked(Note note);
        void onEmailBadgeClicked(Note note);
        void onNoteClicked(Note note);
        void onAddToFavoriteClicked(Note note);
        void onMoreButtonClicked(Note note);
    }


    private List<Note> notes,filteredNotes;
    private Context context;
    private Listener listener;
    private Map<Integer, Boolean> emailFlag, contactFlag;
    private Map<Integer, String> bgColor;
    private NoteFilteringTask noteFilteringTask;
    private NoteSortingTask noteSortingTask;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView noteHeader,noteText,noteTime;
        LinearLayout badgeHolder,emailBadge,contactBadge;
        ImageView addToFavoriteButton,moreButton;
        ConstraintLayout noteRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteHeader = itemView.findViewById(R.id.note_header);
            noteText = itemView.findViewById(R.id.note_text);
            noteTime = itemView.findViewById(R.id.note_time);
            badgeHolder = itemView.findViewById(R.id.badge_holder);
            addToFavoriteButton = itemView.findViewById(R.id.favorite_button);
            moreButton = itemView.findViewById(R.id.more_button);
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
        viewHolder.noteText.setText(UtilityTasks.truncateText(note.getNoteText(),Constants.MAX_NOTE_TEXT_LENGTH,Constants.DOTS));
        viewHolder.noteTime.setText(UtilityTasks.getHumanReadableTime(note.getCreationTime()));
        if(note.isImportant())
        {
            viewHolder.addToFavoriteButton.setImageResource(R.drawable.star_gold);
        }
        else
        {
            viewHolder.addToFavoriteButton.setImageResource(R.drawable.star_border);
        }
        if(emailFlag.get(i)==null)
        {
            bindEmailBadgeToViewHolder(note,viewHolder,i);
        }
        else
        {
            if(emailFlag.get(i))
            {
                bindEmailBadgeToViewHolder(note,viewHolder,i);
            }
            else
            {
                viewHolder.badgeHolder.removeView(viewHolder.emailBadge);
            }
        }
        if(contactFlag.get(i)==null)
        {
            bindContactBadgeToViewHolder(note,viewHolder,i);
        }
        else
        {
            if(contactFlag.get(i))
            {
                bindContactBadgeToViewHolder(note,viewHolder,i);
            }

            else
            {
                viewHolder.badgeHolder.removeView(viewHolder.contactBadge);
            }
        }
        if(bgColor.get(i)==null)
        {
            setNoteBackgroundColor(viewHolder.noteRow, note.getBackgroundColor());
//            Log.e("BGColor","Null for postiton "+i+" Note "+note.toString());
            bgColor.put(i,note.getBackgroundColor());
        }
        else
        {
            setNoteBackgroundColor(viewHolder.noteRow, bgColor.get(i));
        }
        setClickListener(note,viewHolder.noteRow, viewHolder.contactBadge, viewHolder.emailBadge, viewHolder.addToFavoriteButton,viewHolder.moreButton);

    }

    @Override
    public int getItemCount() {
        if(notes!=null)
            return notes.size();
        return 0;
    }

    private void bindEmailBadgeToViewHolder(Note note,NoteListAdapter.ViewHolder viewHolder, int i)
    {
        if(note.getEmailPriority()!=Constants.ZERO)
        {
            viewHolder.badgeHolder.removeView(viewHolder.emailBadge);
            viewHolder.badgeHolder.setVisibility(View.VISIBLE);
            viewHolder.emailBadge = getEmailBadge();
            viewHolder.badgeHolder.addView(viewHolder.emailBadge);
            emailFlag.put(i,true);
        }
        else
        {
            viewHolder.badgeHolder.removeView(viewHolder.emailBadge);
            emailFlag.put(i,false);
        }
    }

    private void bindContactBadgeToViewHolder(Note note,NoteListAdapter.ViewHolder viewHolder, int i)
    {
        if(note.getContactPriority()!=Constants.ZERO)
        {
            viewHolder.badgeHolder.removeView(viewHolder.contactBadge);
            viewHolder.badgeHolder.setVisibility(View.VISIBLE);
            viewHolder.contactBadge = getContactBadge();
            viewHolder.badgeHolder.addView(viewHolder.contactBadge);
            contactFlag.put(i,true);
        }
        else
        {
            viewHolder.badgeHolder.removeView(viewHolder.contactBadge);
            contactFlag.put(i,false);
        }
    }

    private void setNoteBackgroundColor(View view,String colorName)
    {
        if(!colorName.equals(BackgroundColors.WHITE))
        {
            view.setBackgroundColor(context.getResources().getColor(BackgroundColors.getColorMaps().get(colorName)));
            view.getBackground().setAlpha(Constants.BACKGROUND_OPACITY);
        }
        else
        {
            view.setBackgroundColor(context.getResources().getColor(BackgroundColors.getColorMaps().get(colorName)));
        }

    }

    public void bindNotes(List<Note> notes) {
        this.notes = notes;
        this.filteredNotes = notes;
        initDS();
        notifyDataSetChanged();
        noteFilteringTask = new NoteFilteringTask(filteredNotes,notes);
        noteSortingTask = new NoteSortingTask(notes,this);
        noteFilteringTask.setListener(this);

    }

    public void bindFilteredNotes(List<Note> notes) {
        this.notes = new ArrayList<>(notes);
        initDS();
        notifyDataSetChanged();

    }

    public void bindSortedNotes(List<Note> notes) {
        this.notes = new ArrayList<>(notes);
        initDS();
        notifyDataSetChanged();
    }

    private LinearLayout getEmailBadge()
    {
        return (LinearLayout) ((LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.email_badge,null);
    }

    private LinearLayout getContactBadge()
    {
        return (LinearLayout) ((LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.contact_badge,null);
//                .findViewById(R.id.contact_badge);
    }

    private void initDS()
    {
        this.emailFlag = new HashMap<>();
        this.contactFlag = new HashMap<>();
        this.bgColor = new HashMap<>();
    }

    private void setClickListener(final Note note, View... views)
    {

        views[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteClicked(note);
            }
        });

        if(views[1]!=null)
        {
            views[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onContactBadgeClicked(note);
                }
            });
        }

        if(views[2]!=null)
        {
            views[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEmailBadgeClicked(note);
                }
            });
        }

        if(views[3]!=null)
        {
            views[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.setImportant(!note.isImportant());
                    notifyDataSetChanged();
                    listener.onAddToFavoriteClicked(note);
                }
            });
        }

        if(views[4]!=null)
        {
            views[4].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMoreButtonClicked(note);
                }
            });
        }



    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public NoteFilteringTask getNoteFilteringTask() {
        return noteFilteringTask;
    }

    public NoteSortingTask getNoteSortingTask() {
        return noteSortingTask;
    }
}

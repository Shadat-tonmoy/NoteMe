package com.stcodesapp.noteit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.EventTypes;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.models.Note;

public class MoreOptionsBottomSheets extends BottomSheetDialogFragment {

    public interface Listener{
        void onDeleteNoteClicked(Note note);
        void onDeleteContactClicked(Note note);
        void onDeleteEmailClicked(Note note);
        void onDeleteImageClicked(Note note);
        void onDeleteAudioClicked(Note note);

    }

    private Listener listener;
    private TextView deleteContacts,deleteEmails,deleteImages,deleteAudios,deleteNote;
    private Note note;
    private MoreOptionsBottomSheets moreOptionsBottomSheets;


    public static MoreOptionsBottomSheets newInstance(Bundle args) {
        MoreOptionsBottomSheets fragment = new MoreOptionsBottomSheets();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args!=null)
        {
            note = (Note) args.getSerializable(Tags.NOTE);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_option_bottom_sheet_layout,container,false);
        inflateUIElements(view);
        initUserInteractions();
        return view;
    }

    private void inflateUIElements(View view)
    {
        deleteContacts = view.findViewById(R.id.delete_contacts);
        deleteEmails = view.findViewById(R.id.delete_emails);
        deleteImages = view.findViewById(R.id.delete_images);
        deleteAudios = view.findViewById(R.id.delete_audios);
        deleteNote = view.findViewById(R.id.delete_note);

    }

    private void initUserInteractions()
    {
        setClickListener(deleteContacts,EventTypes.DELETE_CONTACT_BUTTON_CLICKED);
        setClickListener(deleteEmails,EventTypes.DELETE_EMAIL_BUTTON_CLICKED);
        setClickListener(deleteImages,EventTypes.DELETE_IMAGE_BUTTON_CLICKED);
        setClickListener(deleteAudios,EventTypes.DELETE_AUDIO_BUTTON_CLICKED);
        setClickListener(deleteNote,EventTypes.DELETE_NOTE_BUTTON_CLICKED);

    }

    private void setClickListener(View view, final int eventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (eventType)
                {
                    case EventTypes.DELETE_CONTACT_BUTTON_CLICKED:
                        listener.onDeleteContactClicked(note);
                        break;
                    case EventTypes.DELETE_EMAIL_BUTTON_CLICKED:
                        listener.onDeleteEmailClicked(note);
                        break;
                    case EventTypes.DELETE_IMAGE_BUTTON_CLICKED:
                        listener.onDeleteImageClicked(note);
                        break;
                    case EventTypes.DELETE_AUDIO_BUTTON_CLICKED:
                        listener.onDeleteAudioClicked(note);
                        break;
                    case EventTypes.DELETE_NOTE_BUTTON_CLICKED:
                        listener.onDeleteNoteClicked(note);
                        break;
                }
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}

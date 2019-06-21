package com.stcodesapp.noteit.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.adapter.PhoneEmailListAdapter;
import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.FragmentTags;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.databaseTasks.DatabaseTasks;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.ContactSelectTask;
import com.stcodesapp.noteit.tasks.databaseTasks.selectionTasks.EmailSelectTask;

import java.util.List;

public class PhoneOrEmailListBottomSheets extends BottomSheetDialogFragment implements ContactSelectTask.Listener, EmailSelectTask.Listener {

    public interface Listener{
        void onPhoneNoOptionSelected(int phoneNoOption);

    }

    private Listener listener;
    private DatabaseTasks databaseTasks;
    private PhoneEmailListAdapter phoneEmailListAdapter;
    private RecyclerView objectList;
    private boolean isContact;
    private long noteId;

    public static PhoneOrEmailListBottomSheets newInstance(Bundle args) {
        PhoneOrEmailListBottomSheets fragment = new PhoneOrEmailListBottomSheets();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        databaseTasks = new DatabaseTasks(requireContext());
        if(args!=null)
        {
            noteId = args.getLong(FragmentTags.NOTE_ID, Constants.ZERO);
            isContact = args.getBoolean(FragmentTags.IS_CONTACT,false);
        }
        if(isContact)
        {
            ContactSelectTask contactSelectTask = databaseTasks.getContactSelectTask(this);
            contactSelectTask.execute(noteId);
        }
        else
        {
            EmailSelectTask emailSelectTask = databaseTasks.getEmailSelectTask(this);
            emailSelectTask.execute(noteId);

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_no_list_bottom_sheet_layout,container,false);
        inflateUIElements(view);
        initUserInteractions();
        return view;
    }

    private void inflateUIElements(View view)
    {
        objectList= view.findViewById(R.id.object_list);
        ComponentType componentType = ComponentType.EMAIL;
        if(isContact)
            componentType = ComponentType.CONTACT;
        phoneEmailListAdapter = new PhoneEmailListAdapter(requireActivity(),componentType);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        objectList.setLayoutManager(layoutManager);
        objectList.setAdapter(phoneEmailListAdapter);
    }

    private void initUserInteractions()
    {
//        setClickListener(manualPhoneNumberOption,EventTypes.MANUAL_PHONE_NO_OPTION_CLICKED);
//        setClickListener(pickFromContactOption,EventTypes.PICK_FROM_CONTACT_OPTION_CLICKED);

    }

    private void setClickListener(View view, final int eventType)
    {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhoneNoOptionSelected(eventType);
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onContactFetched(List<Contact> fetchedContact) {
        phoneEmailListAdapter.bindObjects(fetchedContact);
    }

    @Override
    public void onEmailFetched(List<Email> fetchedEmails) {
        phoneEmailListAdapter.bindObjects(fetchedEmails);

    }
}

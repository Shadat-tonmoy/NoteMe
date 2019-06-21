package com.stcodesapp.noteit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.ComponentType;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.RequestCode;
import com.stcodesapp.noteit.constants.Tags;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;
import com.stcodesapp.noteit.tasks.filteringTasks.ContactFilteringTask;
import com.stcodesapp.noteit.tasks.filteringTasks.EmailFilteringTask;
import com.stcodesapp.noteit.ui.activities.CheckListActivity;

import java.util.ArrayList;
import java.util.List;

public class PhoneEmailListAdapter extends RecyclerView.Adapter<PhoneEmailListAdapter.ViewHolder> implements EmailFilteringTask.Listener, ContactFilteringTask.Listener{


    private List<? extends Object> objects,filteredObject;
    private Context context;
    private boolean isContact;
    private EmailFilteringTask emailFilteringTask;
    private ContactFilteringTask contactFilteringTask;
    private ComponentType componentType;

    @Override
    public void onEmailFiltered(List<Email> filteredEmail) {
        bindFilteredEmail(filteredEmail);
    }

    @Override
    public void onContactFiltered(List<Contact> filteredContact) {
        bindFilteredContact(filteredContact);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,number;
        ImageView copyMenu, sendMenu,leftIcon;
        ConstraintLayout checkListRow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            copyMenu = itemView.findViewById(R.id.copy_menu);
            sendMenu = itemView.findViewById(R.id.send_menu);
            leftIcon = itemView.findViewById(R.id.icon);
            checkListRow = itemView.findViewById(R.id.main_layout);
        }
    }

    public PhoneEmailListAdapter(Context context, ComponentType componentType)
    {
        this.context = context;
        this.componentType = componentType;
    }

    @NonNull
    @Override
    public PhoneEmailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_single_row,viewGroup,false);
        if(componentType==ComponentType.EMAIL)
            listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.email_single_row,viewGroup,false);
        else if(componentType==ComponentType.CHECKLIST)
            listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.email_single_row,viewGroup,false);
        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneEmailListAdapter.ViewHolder viewHolder, int i) {
        if(componentType==ComponentType.CONTACT)
        {
            Contact contact = (Contact) objects.get(i);
            viewHolder.name.setText(contact.getDisplayName());
            viewHolder.number.setText(contact.getPhoneNumber());
        }
        else if(componentType==ComponentType.EMAIL)
        {
            Email email= (Email) objects.get(i);
            viewHolder.name.setText(email.getEmailName());
            viewHolder.number.setText(email.getEmailID());
        }
        else if(componentType==ComponentType.CHECKLIST)
        {
            CheckList checkList = (CheckList) objects.get(i);
            viewHolder.name.setText(checkList.getCheckListTitle());
            viewHolder.number.setText(context.getResources().getString(R.string.check_list_desc));
            viewHolder.leftIcon.setImageResource(R.drawable.check_list_black);
            viewHolder.copyMenu.setVisibility(View.GONE);
            viewHolder.sendMenu.setVisibility(View.GONE);
            setCheckListClickListener(checkList,viewHolder.checkListRow);
        }
        setClickListener(objects.get(i),viewHolder.copyMenu, viewHolder.sendMenu);

    }

    @Override
    public int getItemCount() {
        if(objects!=null)
            return objects.size();
        return 0;
    }

    public void bindObjects(List<? extends Object> objects) {
        this.objects = objects;
        this.filteredObject = objects;
        this.emailFilteringTask = new EmailFilteringTask((List<Email>)filteredObject,(List<Email>) objects,this);
        this.contactFilteringTask = new ContactFilteringTask((List<Contact>)filteredObject,(List<Contact>) objects,this);
        notifyDataSetChanged();
    }

    public void bindFilteredEmail(List<Email> emails)
    {
        this.objects= new ArrayList<>(emails);
        notifyDataSetChanged();
    }

    public void bindFilteredContact(List<Contact> contacts)
    {
        this.objects= new ArrayList<>(contacts);
        notifyDataSetChanged();
    }



    private void setClickListener(final Object object, View... views)
    {

        if(views[0]!=null)
        {
            views[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = Constants.EMPTY_STRING;
                    if(isContact)
                        text = ((Contact)object).getPhoneNumber();
                    else text = ((Email)object).getEmailID();
                    UtilityTasks.copyToClipboard(context,text);
                }
            });
        }

        if(views[1]!=null)
        {
            views[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isContact)
                    {
                        String phoneNumber= ((Contact)object).getPhoneNumber();
                        UtilityTasks.makeCall(context,phoneNumber);
                    }
                    else
                    {
                        String emailId = ((Email)object).getEmailID();
                        UtilityTasks.sendEmail((Activity) context,emailId);
                    }
                }
            });
        }
    }

    private void setCheckListClickListener(final CheckList checkList, View view)
    {
        if(view!=null)
        {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putSerializable(Tags.CHECK_LIST,checkList);
                    args.putBoolean(Tags.CHECK_LIST_UPDATING,true);
                    args.putLong(Tags.NOTE_ID,checkList.getNoteId());
                    toCheckListScreen(args);

                }
            });
        }

    }

    public void toCheckListScreen(Bundle args)
    {
        Intent intent = new Intent(context, CheckListActivity.class);
        intent.putExtras(args);
        ((Activity)context).startActivityForResult(intent, RequestCode.ADD_SINGLE_CHECKLIST);
    }



    public EmailFilteringTask getEmailFilteringTask() {
        return emailFilteringTask;
    }

    public ContactFilteringTask getContactFilteringTask() {
        return contactFilteringTask;
    }
}

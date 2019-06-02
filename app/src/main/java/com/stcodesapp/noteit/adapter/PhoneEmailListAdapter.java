package com.stcodesapp.noteit.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.BackgroundColors;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.tasks.UtilityTasks;
import com.stcodesapp.noteit.tasks.filteringTasks.ContactFilteringTask;
import com.stcodesapp.noteit.tasks.filteringTasks.EmailFilteringTask;

import java.util.ArrayList;
import java.util.List;

public class PhoneEmailListAdapter extends RecyclerView.Adapter<PhoneEmailListAdapter.ViewHolder> implements EmailFilteringTask.Listener, ContactFilteringTask.Listener{


    private List<? extends Object> objects,filteredObject;
    private Context context;
    private boolean isContact;
    private EmailFilteringTask emailFilteringTask;
    private ContactFilteringTask contactFilteringTask;

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
        ImageView copyMenu, sendMenu;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            copyMenu = itemView.findViewById(R.id.copy_menu);
            sendMenu = itemView.findViewById(R.id.send_menu);
        }
    }

    public PhoneEmailListAdapter(Context context, boolean isContact)
    {
        this.context = context;
        this.isContact = isContact;
    }

    @NonNull
    @Override
    public PhoneEmailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_single_row,viewGroup,false);
        if(!isContact)
            listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.email_single_row,viewGroup,false);
        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneEmailListAdapter.ViewHolder viewHolder, int i) {
        if(isContact)
        {
            Contact contact = (Contact) objects.get(i);
            viewHolder.name.setText(contact.getDisplayName());
            viewHolder.number.setText(contact.getPhoneNumber());
        }
        else
        {
            Email email= (Email) objects.get(i);
            viewHolder.name.setText(email.getEmailName());
            viewHolder.number.setText(email.getEmailID());
        }
        setClickListener(objects.get(i),viewHolder.copyMenu, viewHolder.sendMenu/*, viewHolder.editNote, viewHolder.deleteNote, viewHolder.moreOption*/);

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

    public EmailFilteringTask getEmailFilteringTask() {
        return emailFilteringTask;
    }

    public ContactFilteringTask getContactFilteringTask() {
        return contactFilteringTask;
    }
}

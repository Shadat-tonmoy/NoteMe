package com.stcodesapp.noteit.tasks.filteringTasks;

import android.widget.Filter;
import android.widget.Filterable;

import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFilteringTask implements Filterable {

    public interface Listener{
        void onContactFiltered(List<Contact> filteredContact);
    }

    private List<Contact> filteredContacts,contacts;
    private Listener listener;

    public ContactFilteringTask(List<Contact> filteredContacts, List<Contact> contacts, Listener listener) {
        this.filteredContacts = filteredContacts;
        this.contacts = contacts;
        this.listener = listener;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String text = constraint.toString();
                if(text.isEmpty())
                {
                    filteredContacts = contacts;
                }
                else
                {
                    List<Contact> filteredContactList = new ArrayList<>();
                    for(Contact contact:contacts)
                    {
                        if
                        ( contact.getDisplayName().toLowerCase().contains(text.toLowerCase()) ||
                            contact.getPhoneNumber().toLowerCase().contains(text.toLowerCase()))
                            filteredContactList.add(contact);
                    }
                    filteredContacts = filteredContactList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredContacts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredContacts = (List<Contact>) results.values;
                listener.onContactFiltered(filteredContacts);

            }
        };
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}


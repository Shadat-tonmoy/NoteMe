package com.stcodesapp.noteit.tasks.filteringTasks;

import android.widget.Filter;
import android.widget.Filterable;

import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Note;

import java.util.ArrayList;
import java.util.List;

public class EmailFilteringTask implements Filterable {

    public interface Listener{
        void onEmailFiltered(List<Email> filteredEmail);
    }

    private List<Email> filteredEmails,emails;
    private Listener listener;

    public EmailFilteringTask(List<Email> filteredEmails, List<Email> emails,Listener listener) {
        this.filteredEmails = filteredEmails;
        this.emails = emails;
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
                    filteredEmails = emails;
                }
                else
                {
                    List<Email> filteredEmailList = new ArrayList<>();
                    for(Email email:emails)
                    {
                        if
                        ( email.getEmailName().toLowerCase().contains(text.toLowerCase()) ||
                            email.getEmailID().toLowerCase().contains(text.toLowerCase()))
                            filteredEmailList.add(email);
                    }
                    filteredEmails = filteredEmailList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredEmails;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredEmails = (List<Email>) results.values;
                listener.onEmailFiltered(filteredEmails);

            }
        };
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}


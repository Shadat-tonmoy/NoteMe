package com.stcodesapp.noteit.tasks.filteringTasks;

import android.widget.Filter;
import android.widget.Filterable;

import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class CheckListFilteringTask implements Filterable {

    public interface Listener{
        void onCheckListFiltered(List<CheckList> filteredCheckList);
    }

    private List<CheckList> filteredCheckList,checkLists;
    private Listener listener;

    public CheckListFilteringTask(List<CheckList> filteredCheckLists, List<CheckList> checkLists , Listener listener) {
        this.filteredCheckList = filteredCheckLists;
        this.checkLists= checkLists;
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
                    filteredCheckList = checkLists;
                }
                else
                {
                    List<CheckList> filteredCheckListList = new ArrayList<>();
                    for(CheckList checkList:checkLists)
                    {
                        if
                        ( checkList.getCheckListTitle().toLowerCase().contains(text.toLowerCase()))
                            filteredCheckListList.add(checkList);
                    }
                    filteredCheckList = filteredCheckListList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCheckList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCheckList = (List<CheckList>) results.values;
                listener.onCheckListFiltered(filteredCheckList);

            }
        };
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}


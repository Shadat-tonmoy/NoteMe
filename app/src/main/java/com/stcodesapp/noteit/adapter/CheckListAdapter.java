package com.stcodesapp.noteit.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.SingleCheckList;
import com.stcodesapp.noteit.tasks.filteringTasks.ContactFilteringTask;
import com.stcodesapp.noteit.tasks.filteringTasks.EmailFilteringTask;
import com.stcodesapp.noteit.tasks.utilityTasks.UtilityTasks;

import java.util.ArrayList;
import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {


    private List<? extends Object> checkListObjects;
    private Context context;
    private boolean doubleColumn;


    public class ViewHolder extends RecyclerView.ViewHolder{

        EditText itemTitleField;
        CheckBox itemCheckBox;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitleField = itemView.findViewById(R.id.check_item_title_field);
            itemCheckBox = itemView.findViewById(R.id.check_item_checkbox);

        }
    }

    public CheckListAdapter(Context context, boolean doubleColumn)
    {
        this.context = context;
        this.doubleColumn= doubleColumn;

    }

    @NonNull
    @Override
    public CheckListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_checklist_single_row,viewGroup,false);
        if(doubleColumn)
        {
            //set double column view
            listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.email_single_row,viewGroup,false);
        }
        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListAdapter.ViewHolder viewHolder, int i) {
        if(doubleColumn)
        {
            //set double column logics
        }
        else
        {
            SingleCheckList singleCheckList = (SingleCheckList) checkListObjects.get(i);
            viewHolder.itemTitleField.setText(singleCheckList.getTitle());
            viewHolder.itemCheckBox.setChecked(singleCheckList.isChecked());
        }
        /*setClickListener(objects.get(i),viewHolder.copyMenu, viewHolder.sendMenu*//*, viewHolder.editNote, viewHolder.deleteNote, viewHolder.moreOption*//*);*/

    }

    @Override
    public int getItemCount() {
        if(checkListObjects!=null)
            return checkListObjects.size();
        return 0;
    }

    public void bindCheckListObjects(List<? extends Object> checkListObjects) {
        this.checkListObjects= checkListObjects;
        notifyDataSetChanged();
    }



    private void setClickListener(final Object object, View... views)
    {

    }
}

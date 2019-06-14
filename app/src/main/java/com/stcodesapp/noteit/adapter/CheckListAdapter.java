package com.stcodesapp.noteit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.listeners.CheckListItemTextChangeListener;
import com.stcodesapp.noteit.models.ChecklistItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {


    private List<ChecklistItem> checkListObjects;
    private Context context;
    private boolean doubleColumn;
    private Map<Integer,String> fieldValues;


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
        this.fieldValues = new HashMap<>();

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
            ChecklistItem checklistItem = checkListObjects.get(i);
            String fieldValue = checklistItem.getField1();
            Log.e("DebFieldValue",fieldValue+" @ position "+i);
            /*if(fieldValues.get(i)!=null)
            {
                fieldValue = fieldValues.get(i);
                checklistItem.setField1(fieldValue);
                Log.e("DebBindingFieldValue","At Postion "+i+" Value "+fieldValue+" Frin Map");
            }
            Log.e("DebBindingFieldValue","At Postion "+i+" Value "+fieldValue);*/
            viewHolder.itemTitleField.setText(fieldValue);
            viewHolder.itemCheckBox.setChecked(checklistItem.isChecked());
            viewHolder.itemTitleField.addTextChangedListener(new CheckListItemTextChangeListener(checklistItem,Constants.CHECKLIST_FIELD_1, i, fieldValues));
        }
        /*setClickListener(objects.get(i),viewHolder.copyMenu, viewHolder.sendMenu*//*, viewHolder.editNote, viewHolder.deleteNote, viewHolder.moreOption*//*);*/

    }

    @Override
    public int getItemCount() {
        if(checkListObjects!=null)
            return checkListObjects.size();
        return 0;
    }

    public void bindCheckListObjects(List<ChecklistItem> checkListObjects) {
        this.checkListObjects= checkListObjects;
        notifyDataSetChanged();
    }

    public void addEmptyChecklistItem() {
        ChecklistItem checklistItem = new ChecklistItem(Constants.EMPTY_STRING,false);
        if(doubleColumn)
            checklistItem = new ChecklistItem(Constants.EMPTY_STRING,Constants.EMPTY_STRING,false);

        checkListObjects.add(checklistItem);
        Toast.makeText(context, context.getResources().getString(R.string.new_item_added), Toast.LENGTH_SHORT).show();
        notifyItemChanged(checkListObjects.size()-1);


    }

    public List<ChecklistItem> getCheckListObjects() {
        return checkListObjects;
    }

    private void setClickListener(final Object object, View... views)
    {

    }
}

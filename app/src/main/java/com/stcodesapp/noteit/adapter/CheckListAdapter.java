package com.stcodesapp.noteit.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.stcodesapp.noteit.R;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.CheckList;
import com.stcodesapp.noteit.models.ChecklistItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder> {

    private List<ChecklistItem> checkListObjects;
    private Context context;
    private boolean doubleColumn;
    private Map<Integer,String> fieldValues;
    private Map<Integer,Boolean> checkedValues;


    public class ViewHolder extends RecyclerView.ViewHolder{

        EditText itemTitleField;
        CheckBox itemCheckBox;
        TextView checkListItemRemoveButton;
        SwipeRevealLayout swipeRevealLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitleField = itemView.findViewById(R.id.check_item_title_field);
            itemCheckBox = itemView.findViewById(R.id.check_item_checkbox);
            checkListItemRemoveButton = itemView.findViewById(R.id.check_list_remove_btn);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_reveal_root);

        }
    }

    public CheckListAdapter(Context context, boolean doubleColumn)
    {
        this.context = context;
        this.doubleColumn= doubleColumn;
        this.fieldValues = new HashMap<>();
        this.checkedValues = new HashMap<>();

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
    public void onBindViewHolder(@NonNull final CheckListAdapter.ViewHolder viewHolder, final int i) {
        if(doubleColumn)
        {
            //set double column logics
        }
        else
        {
            final ChecklistItem checklistItem = checkListObjects.get(i);
            String fieldValue = checklistItem.getField1();
            Log.e("Binding",checkListObjects.toString()+"Size "+checkListObjects.size()+" Position "+i);
            viewHolder.swipeRevealLayout.close(false);
            viewHolder.itemTitleField.setText(fieldValue);
            viewHolder.itemCheckBox.setChecked(checklistItem.isChecked());
            viewHolder.itemTitleField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                    {
                        String data = viewHolder.itemTitleField.getText().toString();
                        checklistItem.setField1(data);
                    }
                }
            });
            viewHolder.itemTitleField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        String data = viewHolder.itemTitleField.getText().toString();
                        checklistItem.setField1(data);
                    }
                    return false;
                }
            });
            viewHolder.itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checklistItem.setChecked(isChecked);
                }
            });
            setClickListener(checklistItem,i,viewHolder.checkListItemRemoveButton);
        }

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

        if(checkListObjects==null)
            checkListObjects = new ArrayList<>();
        checkListObjects.add(checklistItem);
        Toast.makeText(context, context.getResources().getString(R.string.new_item_added), Toast.LENGTH_SHORT).show();
        notifyItemInserted(checkListObjects.size()-1);


    }

    public List<ChecklistItem> getCheckListObjects() {
        return checkListObjects;
    }

    private void setClickListener(final Object object, final int position, View... views)
    {
        views[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChecklistItem checklistItem = (ChecklistItem) object;
                checkListObjects.remove(object);
                notifyItemRemoved(position);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

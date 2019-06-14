package com.stcodesapp.noteit.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.models.ChecklistItem;

import java.util.Map;

public class CheckListItemTextChangeListener implements TextWatcher {

    private final ChecklistItem checklistItem;
    private int field,position;
    private Map<Integer,String> fieldValues;

    public CheckListItemTextChangeListener(ChecklistItem checklistItem, int field, int position, Map<Integer, String> fieldValues) {
        this.checklistItem = checklistItem;
        this.field = field;
        this.position = position;
        this.fieldValues = fieldValues;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(field== Constants.CHECKLIST_FIELD_1)
        {
            checklistItem.setField1(s.toString());
            fieldValues.put(position,s.toString());
//            Log.e("DebOnTextChanged",position+" "+fieldValues.get(position)+" Map "+fieldValues.toString());
            Log.e("DebOnTextChanged",position+" "+checklistItem.getField1());
        }
        else if(field== Constants.CHECKLIST_FIELD_2)
        {
            checklistItem.setField2(s.toString());
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

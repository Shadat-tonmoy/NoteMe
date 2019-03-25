package com.stcodesapp.noteit.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.stcodesapp.noteit.R;

public class InputField implements InputFieldUI {

    private Context context;
    private View rootView;
    private TextView clearButton,doneButton;
    private EditText inputField;
    private InputFieldListener inputFieldListener;

    public InputField(Context context)
    {
        this.context = context;


    }

    @Override
    public void setInputFieldListener(InputFieldListener inputFieldListener) {
        this.inputFieldListener = inputFieldListener;
    }

    @Override
    public void generateUI() {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = layoutInflater.inflate(R.layout.input_field,null,false);
        clearButton = rootView.findViewById(R.id.clear_button);
        doneButton = rootView.findViewById(R.id.done_button);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFieldListener.onInputClear(inputField);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFieldListener.onInputSubmit(inputField.getText().toString());
            }
        });

    }

    @Override
    public View getRootView() {
        return rootView;
    }
}

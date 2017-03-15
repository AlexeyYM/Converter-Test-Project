package com.javir.converter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.javir.converter.R;

public class FragmentDenomination extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.layout_fragment_denomination;

    private Button denominationOldButton;
    private Button denominationNewButton;

    public static FragmentDenomination getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentDenomination fragment = new FragmentDenomination();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_denomination));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        denominationOldButton = (Button) view.findViewById(R.id.denominationOldButton);
        denominationNewButton = (Button) view.findViewById(R.id.denominationNewButton);

        denominationOldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denominationOldButton();
            }
        });

        denominationNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denominationNewButton();
            }
        });

        return view;
    }

    public void denominationOldButton() {
        EditText inputOldSum = (EditText) view.findViewById(R.id.inputOld);
        TextView outputOldSum = (TextView) view.findViewById(R.id.outputOldText);

        if(inputOldSum.getText().length() == 0) {
            Toast.makeText(context, "Введите сумму", Toast.LENGTH_LONG).show();
            return;
        }

        double inputValue = Double.parseDouble(inputOldSum.getText().toString());
        double outputValue = convertOld(inputValue);

        outputOldSum.setText(String.format("%,.2f рублей", outputValue));
    }

    public void denominationNewButton() {
        EditText inputNewSum = (EditText) view.findViewById(R.id.inputNew);
        TextView outputNewSum = (TextView) view.findViewById(R.id.outputNewText);

        if(inputNewSum.getText().length() == 0) {
            Toast.makeText(context, "Введите сумму", Toast.LENGTH_LONG).show();
            return;
        }

        double inputValue = Double.parseDouble(inputNewSum.getText().toString());
        double outputValue = convertNew(inputValue);

        outputNewSum.setText(String.format("%,.0f рублей", outputValue));
    }

    public double convertOld(double x) {
        return x / 10000;
    }

    public double convertNew(double x) {
        return x * 10000;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

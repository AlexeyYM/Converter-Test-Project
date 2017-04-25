package com.javir.converter.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.javir.converter.Constants;
import com.javir.converter.MainActivity;
import com.javir.converter.R;

public class FragmentConverter extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.layout_fragment_converter;

    private Button convOldButton;
    private Button convNewButton;
    private SharedPreferences sharedPreferences;
    private double currency;

    public static FragmentConverter getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentConverter fragment = new FragmentConverter();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_converter));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);
        currency = sharedPreferences.getFloat(Constants.PREFERENCE_CURRENCY, 2.0f);

        convOldButton = ((Button) view.findViewById(R.id.convOldButton));
        convNewButton = ((Button) view.findViewById(R.id.convNewButton));

        convOldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convOld();
            }
        });

        convNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convNew();
            }
        });

        return view;
    }

    public void convOld() {
        EditText inputOldSum = (EditText) view.findViewById(R.id.inputConvSumOld);
        TextView outputOldSum = (TextView) view.findViewById(R.id.convOutputSum);

        if(inputOldSum.getText().length() == 0) {
            Toast.makeText(context, "Введите сумму", Toast.LENGTH_LONG).show();
            return;
        }

        double inputValue = Double.parseDouble(inputOldSum.getText().toString());
        double outputValue = convertOld(inputValue, currency);

        outputOldSum.setText(String.format("$%,.2f", outputValue));
    }

    public double convertOld(double inputValue, double currency) {
        return inputValue / (currency * 10000);
    }

    public void convNew() {
        EditText inputOldSum = (EditText) view.findViewById(R.id.inputConvSumNew);
        TextView outputOldSum = (TextView) view.findViewById(R.id.convOutputSum);

        if(inputOldSum.getText().length() == 0) {
            Toast.makeText(context, "Введите сумму", Toast.LENGTH_LONG).show();
            return;
        }

        double inputValue = Double.parseDouble(inputOldSum.getText().toString());
        double outputValue = convertNew(inputValue, currency);

        outputOldSum.setText(String.format("$%,.2f", outputValue));
    }

    public double convertNew(double inputValue, double currency) {
        return inputValue / currency;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

package com.javir.converter.fragments.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.javir.converter.R;
import com.javir.converter.fragments.presenters.FragmentConverterPresenter;
import com.javir.converter.general.AbstractTabFragment;
import com.javir.converter.interfaces.FragmentConverterViewInterface;
import com.javir.converter.model.CurrencyDTO;

import java.util.HashMap;
import java.util.Map;

public class FragmentConverterView extends AbstractTabFragment implements FragmentConverterViewInterface {
    private static final int LAYOUT = R.layout.layout_fragment_converter;

    private Spinner spinnerInitial;
    private Spinner spinnerResult;
    private TextView textViewResultConverter;
    private Button buttonConvert;
    private EditText inputValue;

    private Map<String, CurrencyDTO> currencyDTOs;
    private String[] abbreviations;

    private FragmentConverterPresenter fragmentConverterPresenter;

    private double rateInitial;
    private double rateResult;
    private double result;

    public static FragmentConverterView getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentConverterView fragment = new FragmentConverterView();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_converter));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        currencyDTOs = new HashMap<String, CurrencyDTO>();
        fragmentConverterPresenter = new FragmentConverterPresenter(this);

        textViewResultConverter = (TextView) view.findViewById(R.id.textViewResultConverter);
        inputValue = (EditText) view.findViewById(R.id.inputValue);
        buttonConvert = (Button) view.findViewById(R.id.buttonConvert);

        rateInitial = 0;
        rateResult = 0;
        result = 0;

        initializeCurrencyMap();
        abbreviations = fragmentConverterPresenter.initialAbbreviatons(currencyDTOs);


        spinnerInitial = (Spinner) view.findViewById(R.id.spinnerInitialCurrency);
        ArrayAdapter<String> adapterSpinnerInitial = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, abbreviations);
        adapterSpinnerInitial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInitial.setAdapter(adapterSpinnerInitial);

        AdapterView.OnItemSelectedListener listenerCurrencyInitial = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = (String) adapterView.getItemAtPosition(position);

                if (position == 0) {
                    rateInitial = 1;
                } else {
                    rateInitial = fragmentConverterPresenter.rate(currencyDTOs.get(item).getCurOfficialRate(),
                            currencyDTOs.get(item).getCurScale());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerInitial.setOnItemSelectedListener(listenerCurrencyInitial);

        spinnerResult = (Spinner) view.findViewById(R.id.spinnerResultCurrency);
        ArrayAdapter<String> adapterSpinnerResult = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, abbreviations);
        adapterSpinnerResult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResult.setAdapter(adapterSpinnerResult);

        AdapterView.OnItemSelectedListener listenerCurrencyResult = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String item = (String) adapterView.getItemAtPosition(position);

                if (position == 0) {
                    rateResult = 1;
                } else {
                    rateResult = fragmentConverterPresenter.rate(currencyDTOs.get(item).getCurOfficialRate(),
                            currencyDTOs.get(item).getCurScale());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerResult.setOnItemSelectedListener(listenerCurrencyResult);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputValue.getText().length() == 0) {
                    Toast.makeText(getContext(), getText(R.string.toastEmptyTextInputField).toString(),
                            Toast.LENGTH_LONG).show();
                } else {
                    double value = Double.parseDouble(inputValue.getText().toString());
                    result = fragmentConverterPresenter.convert(value, rateInitial, rateResult);

                    textViewResultConverter.setText(getString(R.string.resultConverterDefaultText).toString() +
                            " " + String.format("%,.2f", result));

                }
            }
        });

        return view;
    }

    @Override
    public void initializeCurrencyMap() {
        currencyDTOs = fragmentConverterPresenter.getCurrencyFromDB(currencyDTOs);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.toastGetDatabaseCurrencyFailed).toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}

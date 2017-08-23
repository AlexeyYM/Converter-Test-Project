package com.javir.converter.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.javir.converter.Constants;
import com.javir.converter.DBHelper;
import com.javir.converter.MainActivity;
import com.javir.converter.R;
import com.javir.converter.dto.CurrencyDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentConverter extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.layout_fragment_converter;

    private Spinner spinnerInitial;
    private Spinner spinnerResult;
    private TextView textViewResultConverter;
    private Button buttonConvert;
    private EditText inputValue;

    private Map<String, CurrencyDTO> currencyDTOs;
    private String[] abbreviations;

    private DBHelper dbHelper;

    private double rateInitial;
    private double rateResult;
    private double result;

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
        currencyDTOs = new HashMap<String, CurrencyDTO>();
        dbHelper = new DBHelper(getContext());

        textViewResultConverter = (TextView) view.findViewById(R.id.textViewResultConverter);
        inputValue = (EditText) view.findViewById(R.id.inputValue);
        buttonConvert = (Button) view.findViewById(R.id.buttonConvert);

        rateInitial = 0;
        rateResult = 0;
        result = 0;

        getCurrency();
        abbreviations = initialAbbreviatons(currencyDTOs);


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
                    rateInitial = currencyDTOs.get(item).getCurOfficialRate() / currencyDTOs.get(item).getCurScale();
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
                    rateResult = currencyDTOs.get(item).getCurOfficialRate() / currencyDTOs.get(item).getCurScale();
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
                    result = value * (rateInitial / rateResult);

                    textViewResultConverter.setText(getString(R.string.resultConverterDefaultText).toString() +
                            " " + String.format("%,.2f", result));

                }
            }
        });

        return view;
    }

    public void getCurrency() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_CURRENCY, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int curIdIndex = cursor.getColumnIndex(DBHelper.CUR_ID);
            int dateIndex = cursor.getColumnIndex(DBHelper.DATE);
            int abbreviationIndex = cursor.getColumnIndex(DBHelper.ABBREVIATION);
            int scaleIndex = cursor.getColumnIndex(DBHelper.SCALE);
            int nameIndex = cursor.getColumnIndex(DBHelper.NAME);
            int rateIndex = cursor.getColumnIndex(DBHelper.RATE);
            do {
                CurrencyDTO currencyDTO = new CurrencyDTO();
                currencyDTO.setCurID(cursor.getInt(curIdIndex));
                currencyDTO.setDate(cursor.getString(dateIndex));
                currencyDTO.setCurAbbreviation(cursor.getString(abbreviationIndex));
                currencyDTO.setCurScale(cursor.getInt(scaleIndex));
                currencyDTO.setCurName(cursor.getString(nameIndex));
                currencyDTO.setCurOfficialRate(cursor.getDouble(rateIndex));

                currencyDTOs.put(currencyDTO.getCurAbbreviation(), currencyDTO);
            } while ((cursor.moveToNext()));
        } else {
            Toast.makeText(getContext(), getString(R.string.toastGetDatabaseCurrencyFailed).toString(),
                    Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }

    private String[] initialAbbreviatons(Map<String, CurrencyDTO> currencyDTOs) {
        String[] result = new String[currencyDTOs.size() + 1];
        result[0] = getString(R.string.abbreviationBelRub).toString();
        int count = 1;

        for (Map.Entry<String, CurrencyDTO> cur : currencyDTOs.entrySet()) {
            result[count] = cur.getKey();
            count++;
        }

        return result;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

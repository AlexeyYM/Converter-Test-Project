package com.javir.converter.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.javir.converter.App;
import com.javir.converter.DBHelper;
import com.javir.converter.MainActivity;
import com.javir.converter.R;
import com.javir.converter.adapter.CurrencyListAdapter;
import com.javir.converter.dto.CurrencyDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCurrencyList extends AbstractTabFragment{
    public static final int LAYOUT = R.layout.layout_fragment_currency;

    private List<CurrencyDTO> currency;

    private RecyclerView rv;

    private DBHelper dbHelper;

    public static FragmentCurrencyList getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentCurrencyList fragment = new FragmentCurrencyList();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_currencyList));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        currency = new ArrayList<>();
        dbHelper = new DBHelper(getContext());

        rv = (RecyclerView) view.findViewById(R.id.currencyRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new CurrencyListAdapter(currency));

        getCurrency();

        return view;
    }

    private void getCurrency() {
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

                    currency.add(currencyDTO);
                } while ((cursor.moveToNext()));
        } else {
            Toast.makeText(getContext(), getString(R.string.toastGetDatabaseCurrencyFailed).toString(),
                    Toast.LENGTH_LONG).show();
        }
        cursor.close();

        rv.getAdapter().notifyDataSetChanged();
    }

    private void setContext(Context context) {
        this.context = context;
    }
}

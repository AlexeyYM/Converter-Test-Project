package com.javir.converter.fragments;

import android.content.Context;
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

        getCurrency();

        rv = (RecyclerView) view.findViewById(R.id.currencyRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(new CurrencyListAdapter(currency));

        return view;
    }

    private void getCurrency() {
        App.getApi().getData("0").enqueue(new Callback<List<CurrencyDTO>>() {
            @Override
            public void onResponse(Call<List<CurrencyDTO>> call, Response<List<CurrencyDTO>> response) {
                currency.addAll(response.body());
                Log.d("Tag", currency.get(0).getCurName());
                rv.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CurrencyDTO>> call, Throwable t) {
                Toast.makeText(getContext(), getText(R.string.toastGetCurrencyFailed), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setContext(Context context) {
        this.context = context;
    }
}

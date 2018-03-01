package com.javir.converter.fragments.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.javir.converter.R;
import com.javir.converter.app.App;
import com.javir.converter.fragments.presenters.FragmentCurrencyListPresenter;
import com.javir.converter.general.AbstractTabFragment;
import com.javir.converter.general.CurrencyListAdapter;
import com.javir.converter.general.DividerItemDecoration;
import com.javir.converter.interfaces.FragmentCurrencyListViewInterface;
import com.javir.converter.model.CurrencyModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class FragmentCurrencyListView extends AbstractTabFragment implements FragmentCurrencyListViewInterface {
    public static final int LAYOUT = R.layout.layout_fragment_currency;

    private FragmentCurrencyListPresenter fragmentCurrencyListPresenter;

    private List<CurrencyModel> currency;

    private RecyclerView rv;

    public static FragmentCurrencyListView getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentCurrencyListView fragment = new FragmentCurrencyListView();
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

        rv = (RecyclerView) view.findViewById(R.id.currencyRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(App.getContext(), R.drawable.items_divider)));
        rv.setAdapter(new CurrencyListAdapter(currency));

        fragmentCurrencyListPresenter = new FragmentCurrencyListPresenter(this);

        updateCurrency();

        return view;
    }

    @Override
    public void updateCurrency() {
        fragmentCurrencyListPresenter.updateCurrencyList(currency);

        rv.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showError() {
        /*Toast.makeText(getContext(), getString(R.string.toastGetDatabaseCurrencyFailed).toString(),
                Toast.LENGTH_LONG).show();*/

        Toasty.error(App.getContext(),  getString(R.string.toastGetDatabaseCurrencyFailed).toString(),
                Toast.LENGTH_LONG, true).show();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void setCurrency(List<CurrencyModel> currency) {
        this.currency = currency;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}

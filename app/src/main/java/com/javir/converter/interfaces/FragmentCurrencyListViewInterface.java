package com.javir.converter.interfaces;

import android.content.Context;

import com.javir.converter.model.CurrencyModel;

import java.util.List;

public interface FragmentCurrencyListViewInterface extends MessageView {
    void updateCurrency();

    void setCurrency(List<CurrencyModel> currency);

    void setContext(Context context);
}

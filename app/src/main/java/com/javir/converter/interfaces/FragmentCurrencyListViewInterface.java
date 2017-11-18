package com.javir.converter.interfaces;

import android.content.Context;

import com.javir.converter.model.CurrencyDTO;

import java.util.List;

public interface FragmentCurrencyListViewInterface extends MessageView {
    void updateCurrency();

    void setCurrency(List<CurrencyDTO> currency);

    void setContext(Context context);
}

package com.javir.converter.interfaces;

import com.javir.converter.model.CurrencyModel;

import java.util.List;

public interface MainActivityViewInterface extends MessageView {

    void updateCurrency();

    List<CurrencyModel> getCurrency();

    void setCurrency(List<CurrencyModel> currency);
}

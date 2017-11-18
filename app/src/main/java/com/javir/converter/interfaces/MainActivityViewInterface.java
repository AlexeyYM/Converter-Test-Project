package com.javir.converter.interfaces;

import com.javir.converter.model.CurrencyDTO;

import java.util.List;

public interface MainActivityViewInterface extends MessageView {

    void updateCurrency();

    List<CurrencyDTO> getCurrency();

    void setCurrency(List<CurrencyDTO> currency);
}

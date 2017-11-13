package com.javir.converter.fragments.presenters;

import android.support.annotation.NonNull;

import com.javir.converter.fragments.views.FragmentDenominationView;

/**
 * Created by alexeyy on 13.11.17.
 */

public class FragmentDenominationPresenter {
    private final FragmentDenominationView denominationView;

    public FragmentDenominationPresenter(@NonNull FragmentDenominationView denominationView) {
        this.denominationView = denominationView;
    }

    public double convertOld(double inputValue) {
        return inputValue / 10000;
    }

    public double convertNew(double inputValue) {
        return inputValue * 10000;
    }
}

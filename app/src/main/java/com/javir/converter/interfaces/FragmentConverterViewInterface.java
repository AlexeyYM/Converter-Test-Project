package com.javir.converter.interfaces;

import android.content.Context;

public interface FragmentConverterViewInterface extends MessageView {
    void initializeCurrencyMap();

    void setContext(Context context);
}

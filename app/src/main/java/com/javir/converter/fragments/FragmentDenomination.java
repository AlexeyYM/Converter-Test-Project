package com.javir.converter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javir.converter.R;

/**
 * Created by Элио on 08.02.2017.
 */
public class FragmentDenomination extends AbstractTabFragment {
    private static final int LAYOUT = R.layout.layout_fragment_denomination;

    public static FragmentDenomination getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentDenomination fragment = new FragmentDenomination();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_denomination));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

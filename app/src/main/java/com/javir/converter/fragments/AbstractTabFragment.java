package com.javir.converter.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Элио on 13.02.2017.
 */
public class AbstractTabFragment extends Fragment {
    private String title;

    protected Context context;
    protected View view;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

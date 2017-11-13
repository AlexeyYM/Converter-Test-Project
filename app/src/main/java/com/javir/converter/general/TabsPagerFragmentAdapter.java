package com.javir.converter.general;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.javir.converter.fragments.views.FragmentConverterView;
import com.javir.converter.fragments.views.FragmentCurrencyListView;
import com.javir.converter.fragments.views.FragmentDenominationView;

import java.util.HashMap;
import java.util.Map;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {
    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        initTabsMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, FragmentConverterView.getInstance(context));
        tabs.put(1, FragmentDenominationView.getInstance(context));
        tabs.put(2, FragmentCurrencyListView.getInstance(context));
    }
}

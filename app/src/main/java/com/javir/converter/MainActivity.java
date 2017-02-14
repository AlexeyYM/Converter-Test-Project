package com.javir.converter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.javir.converter.adapter.TabsPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_main;

    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initTabLayout();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu_main);
    }

    private void initTabLayout() {
        viewPager = ((ViewPager) findViewById(R.id.viewPager));
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = ((TabLayout) findViewById(R.id.tabLayout));
        tabLayout.setupWithViewPager(viewPager);
    }
}
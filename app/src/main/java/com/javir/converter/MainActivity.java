package com.javir.converter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.javir.converter.adapter.TabsPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_main;

    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt(Constants.PREFERENCE_THEME, R.style.AppThemeWhite);
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initTabLayout();
    }

    private void choiceTheme() {
        final String[] ITEMS = {"Светлая", "Тёмная"};
        final int[] index = {-1};

        new AlertDialog.Builder(this)
                .setTitle(R.string.choiceThemeTitle)
                .setSingleChoiceItems(ITEMS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Выбрана " + ITEMS[which] + " тема", Toast.LENGTH_SHORT).show();
                        index[0] = which;
                    }
                })
                .setPositiveButton(R.string.choiceThemePositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        switch (index[0]) {
                            case 0:
                                editor.putInt(Constants.PREFERENCE_THEME, R.style.AppThemeWhite)
                                        .apply();
                                recreate();
                                break;
                            case 1:
                                editor.putInt(Constants.PREFERENCE_THEME, R.style.AppThemeBlack)
                                        .apply();
                                recreate();
                                break;
                        }
                    }
                })
                .create()
                .show();
    }

    //Временно захардкодил курсы валют и некоторые строковые ресурсы
    private void choiceCurrency() {
        final String[] ITEMS = {"Русский рубль", "Украинская гривна", "Доллар США", "Евро"};
        final int[] index = {-1};

        new AlertDialog.Builder(this)
                .setTitle(R.string.choiceCurrencyTitle)
                .setSingleChoiceItems(ITEMS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Вы выбрали " + ITEMS[which], Toast.LENGTH_LONG).show();
                        index[0] = which;
                    }
                })
                .setPositiveButton(R.string.choiceThemePositiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        switch (index[0]) {
                            case 0:
                                editor.putFloat(Constants.PREFERENCE_CURRENCY, 0.3f).apply();
                                recreate();
                                break;
                            case 1:
                                editor.putFloat(Constants.PREFERENCE_CURRENCY, 0.07f).apply();
                                recreate();
                                break;
                            case 2:
                                editor.putFloat(Constants.PREFERENCE_CURRENCY, 2.0f).apply();
                                recreate();
                                break;
                            case 3:
                                editor.putFloat(Constants.PREFERENCE_CURRENCY, 2.2f).apply();
                                recreate();
                                break;
                        }
                    }
                })
                .create()
                .show();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.action_theme):
                        choiceTheme();
                        return true;
                    case (R.id.choice_currency):
                        choiceCurrency();
                        return true;
                    default:
                        return false;
                }
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
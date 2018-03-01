package com.javir.converter.fragments.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.javir.converter.R;
import com.javir.converter.app.App;
import com.javir.converter.fragments.presenters.MainActivityPresenter;
import com.javir.converter.general.TabsPagerFragmentAdapter;
import com.javir.converter.model.CurrencyModel;
import com.javir.converter.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivityView extends AppCompatActivity implements com.javir.converter.interfaces.MainActivityViewInterface {
    private static final int LAYOUT = R.layout.activity_main;

    private final String TAG = "MainActivity";

    private MainActivityPresenter mainActivityPresenter;

    private Toolbar toolbar;
    private ViewPager viewPager;

    private List<CurrencyModel> currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt(Constants.PREFERENCE_THEME, R.style.AppThemeWhite);
        setTheme(theme);
        Log.d(TAG, "Theme sets");

        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        mainActivityPresenter = new MainActivityPresenter(this);

        currency = new ArrayList<>();

        initToolbar();
        initTabLayout();
        updateCurrency();
    }

    private void choiceTheme() {
        final String[] ITEMS = {"Светлая", "Тёмная"};
        final int[] index = {-1};

        new AlertDialog.Builder(this)
                .setTitle(R.string.choiceThemeTitle)
                .setSingleChoiceItems(ITEMS, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Toast.makeText(getApplicationContext(), "Выбрана " + ITEMS[which] + " тема", Toast.LENGTH_SHORT).show();*/

                        Toasty.info(App.getContext(), "Выбрана " + ITEMS[which] + " тема", Toast.LENGTH_LONG, true).show();
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
                                Log.d(TAG, "White theme");
                                recreate();
                                Log.d(TAG, "Recreate");
                                break;
                            case 1:
                                editor.putInt(Constants.PREFERENCE_THEME, R.style.AppThemeBlack)
                                        .apply();
                                Log.d(TAG, "Black theme");
                                recreate();
                                Log.d(TAG, "Recreate");
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

    @Override
    public void updateCurrency() {
        currency = mainActivityPresenter.updateCurrency();
    }

    @Override
    public void showSuccess() {
        /*Toast.makeText(getApplicationContext(), getText(R.string.toastGetCurrencySucces).toString(),
                Toast.LENGTH_LONG).show();*/

        Toasty.success(App.getContext(), getText(R.string.toastGetCurrencySucces).toString(),
                Toast.LENGTH_LONG, true).show();
    }

    @Override
    public void showError() {
        /*Toast.makeText(MainActivityView.this, getText(R.string.toastGetCurrencyFailed),
                Toast.LENGTH_LONG).show();*/

        Toasty.error(App.getContext(), getText(R.string.toastGetCurrencyFailed).toString(),
                Toast.LENGTH_LONG, true).show();
    }

    @Override
    public List<CurrencyModel> getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(List<CurrencyModel> currency) {
        this.currency = currency;
    }
}
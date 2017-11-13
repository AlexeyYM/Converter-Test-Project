package com.javir.converter.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.javir.converter.R;
import com.javir.converter.general.TabsPagerFragmentAdapter;
import com.javir.converter.app.App;
import com.javir.converter.dao.DBHelper;
import com.javir.converter.model.CurrencyDTO;
import com.javir.converter.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_main;

    private Toolbar toolbar;
    private ViewPager viewPager;

    private List<CurrencyDTO> currency;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt(Constants.PREFERENCE_THEME, R.style.AppThemeWhite);
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        currency = new ArrayList<>();
        dbHelper = new DBHelper(this);

        initToolbar();
        initTabLayout();
        getCurrency();
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

    private void getCurrency() {
        App.getApi().getData("0").enqueue(new Callback<List<CurrencyDTO>>() {
            @Override
            public void onResponse(Call<List<CurrencyDTO>> call, Response<List<CurrencyDTO>> response) {
                currency.addAll(response.body());

                SQLiteDatabase database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_CURRENCY, null, null);

                for(CurrencyDTO cur : currency) {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(DBHelper.CUR_ID, cur.getCurID());
                    contentValues.put(DBHelper.ABBREVIATION, cur.getCurAbbreviation());
                    contentValues.put(DBHelper.DATE, cur.getDate());
                    contentValues.put(DBHelper.NAME, cur.getCurName());
                    contentValues.put(DBHelper.RATE, cur.getCurOfficialRate());
                    contentValues.put(DBHelper.SCALE, cur.getCurScale());

                    database.insert(DBHelper.TABLE_CURRENCY, null, contentValues);
                }

                Toast.makeText(getApplicationContext(), getText(R.string.toastGetCurrencySucces).toString(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<CurrencyDTO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, getText(R.string.toastGetCurrencyFailed), Toast.LENGTH_LONG).show();
            }
        });
    }
}
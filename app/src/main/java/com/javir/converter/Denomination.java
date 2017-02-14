package com.javir.converter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Элио on 08.02.2017.
 */
public class Denomination extends AppCompatActivity {
    public static final int LAYOUT = R.layout.layout_denomination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void conOldButtonClick(View view) {
        EditText inputOldSum = (EditText) findViewById(R.id.inputOld);
        TextView outputOldSum = (TextView) findViewById(R.id.outputOldText);

        if(inputOldSum.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Введите сумму", Toast.LENGTH_LONG).show();
            return;
        }

        double inputValue = Double.parseDouble(inputOldSum.getText().toString());
        double outputValue = convertOld(inputValue);

        outputOldSum.setText(String.format("%,.2f рублей", outputValue));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_convert:
                Intent intent = new Intent(Denomination.this, ConvertActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_theme:
                choiceTheme();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void choiceTheme() {
        final String[] items = {"Светлая", "Тёмная"};
        new AlertDialog.Builder(this)
                .setTitle(R.string.choiceThemeTitle)
                .setIcon(android.R.drawable.ic_menu_preferences)
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Выбрана тема", Toast.LENGTH_SHORT).show();

                    }
                })
                .create()
                .show();
    }

    public void conNewButtonClick(View view) {
        EditText inputNewSum = (EditText) findViewById(R.id.inputNew);
        TextView outputNewSum = (TextView) findViewById(R.id.outputNewText);

        if(inputNewSum.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Введите сумму", Toast.LENGTH_LONG).show();
            return;
        }

        double inputValue = Double.parseDouble(inputNewSum.getText().toString());
        double outputValue = convertNew(inputValue);

        outputNewSum.setText(String.format("%,.0f рублей", outputValue));
    }

    public double convertOld(double x) {
        return x / 10000;
    }

    public double convertNew(double x) {
        return x * 10000;
    }
}

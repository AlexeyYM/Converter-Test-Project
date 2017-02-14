package com.javir.converter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ConvertActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.layout_convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_convert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_denomination:
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void convOld(View view) {
        EditText inputOldSum = (EditText) findViewById(R.id.inputConvSumOld);
        TextView outputOldSum = (TextView) findViewById(R.id.convOutputSum);

        if(inputOldSum.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Введите сумму", Toast.LENGTH_LONG).show();
            return;
        }

        double inputValue = Double.parseDouble(inputOldSum.getText().toString());
        double outputValue = convertOld(inputValue);

        outputOldSum.setText(String.format("$%,.2f", outputValue));
    }

    public double convertOld(double d) {
        return d / 20000;
    }

    public void convNew(View view) {
        EditText inputOldSum = (EditText) findViewById(R.id.inputConvSumNew);
        TextView outputOldSum = (TextView) findViewById(R.id.convOutputSum);

        if(inputOldSum.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Введите сумму", Toast.LENGTH_LONG).show();
            return;
        }

        double inputValue = Double.parseDouble(inputOldSum.getText().toString());
        double outputValue = convertNew(inputValue);

        outputOldSum.setText(String.format("$%,.2f", outputValue));
    }

    public double convertNew(double d) {
        return d / 2;
    }
}

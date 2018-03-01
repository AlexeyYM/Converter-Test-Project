package com.javir.converter.fragments.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.javir.converter.R;
import com.javir.converter.app.App;
import com.javir.converter.fragments.presenters.FragmentDenominationPresenter;
import com.javir.converter.general.AbstractTabFragment;
import com.javir.converter.interfaces.FragmentDenominationViewInterface;

import es.dmoral.toasty.Toasty;

public class FragmentDenominationView extends AbstractTabFragment implements FragmentDenominationViewInterface {
    private static final int LAYOUT = R.layout.layout_fragment_denomination;

    private FragmentDenominationPresenter fragmentDenominationPresenter;

    private Button denominationOldButton;
    private Button denominationNewButton;

    public static FragmentDenominationView getInstance(Context context) {
        Bundle args = new Bundle();
        FragmentDenominationView fragment = new FragmentDenominationView();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_denomination));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        fragmentDenominationPresenter = new FragmentDenominationPresenter(this);

        denominationOldButton = (Button) view.findViewById(R.id.denominationOldButton);
        denominationNewButton = (Button) view.findViewById(R.id.denominationNewButton);

        denominationOldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denominationOldButton();
            }
        });

        denominationNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denominationNewButton();
            }
        });

        return view;
    }

    @Override
    public void denominationOldButton() {
        EditText inputOldSum = (EditText) view.findViewById(R.id.inputOld);
        TextView outputOldSum = (TextView) view.findViewById(R.id.outputOldText);

        if(inputOldSum.getText().length() == 0) {
            /*Toast.makeText(context, getText(R.string.toastEmptyTextInputField).toString(),
                    Toast.LENGTH_LONG).show();*/

            Toasty.error(App.getContext(),  getText(R.string.toastEmptyTextInputField).toString(),
                    Toast.LENGTH_LONG, true).show();
            return;
        }

        double inputValue = Double.parseDouble(inputOldSum.getText().toString());
        double outputValue = fragmentDenominationPresenter.convertOld(inputValue);

        outputOldSum.setText(String.format("%,.2f рублей", outputValue));
    }

    @Override
    public void denominationNewButton() {
        EditText inputNewSum = (EditText) view.findViewById(R.id.inputNew);
        TextView outputNewSum = (TextView) view.findViewById(R.id.outputNewText);

        if(inputNewSum.getText().length() == 0) {
            /*Toast.makeText(context, getText(R.string.toastEmptyTextInputField).toString(),
                    Toast.LENGTH_LONG).show();*/

            Toasty.error(App.getContext(),  getText(R.string.toastEmptyTextInputField).toString(),
                    Toast.LENGTH_LONG, true).show();
            return;
        }

        double inputValue = Double.parseDouble(inputNewSum.getText().toString());
        double outputValue = fragmentDenominationPresenter.convertNew(inputValue);

        outputNewSum.setText(String.format("%,.0f рублей", outputValue));
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}

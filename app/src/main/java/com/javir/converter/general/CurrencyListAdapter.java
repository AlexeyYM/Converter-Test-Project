package com.javir.converter.general;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javir.converter.R;
import com.javir.converter.app.App;
import com.javir.converter.model.CurrencyModel;
import com.javir.converter.utils.Constants;

import java.util.List;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {
    public static final int LAYOUT = R.layout.currency_item;

    private final String TAG = "CurrencyListAdapter";

    private List<CurrencyModel> data;

    public CurrencyListAdapter(List<CurrencyModel> data) {
        this.data = data;
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT, parent, false);

        return new CurrencyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        SharedPreferences sharedPreferences = App.getContext().getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt(Constants.PREFERENCE_THEME, R.style.AppThemeWhite);

        if (theme == R.style.AppThemeWhite) {
            holder.cardView.setCardBackgroundColor(App.getContext().getResources().getColor(R.color.whiteCardViewBackgound));
            Log.d(TAG, "White card background");
        } else if (theme == R.style.AppThemeBlack) {
            holder.cardView.setCardBackgroundColor(App.getContext().getResources().getColor(R.color.blackCardViewBackground));
            Log.d(TAG, "Black card background");
        }

        holder.titleTextView.setText(data.get(position).getCurName());
        double rate = data.get(position).getCurOfficialRate() / data.get(position).getCurScale();
        holder.rateTextView.setText("" + String.format("%,.2f", rate) + " б.р.");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CurrencyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView titleTextView;
        TextView rateTextView;

        public CurrencyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.currencyCardView);
            titleTextView = (TextView) itemView.findViewById(R.id.currencyCardViewTitle);
            rateTextView = (TextView) itemView.findViewById(R.id.currencyCardViewRate);
        }
    }
}

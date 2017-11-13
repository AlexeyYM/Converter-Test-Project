package com.javir.converter.general;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javir.converter.R;
import com.javir.converter.model.CurrencyDTO;

import java.util.List;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {
    public static final int LAYOUT = R.layout.currency_item;

    private List<CurrencyDTO> data;

    public CurrencyListAdapter(List<CurrencyDTO> data) {
        this.data = data;
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(LAYOUT, parent, false);

        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
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
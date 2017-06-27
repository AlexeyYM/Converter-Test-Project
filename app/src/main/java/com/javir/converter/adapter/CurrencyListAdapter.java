package com.javir.converter.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javir.converter.R;
import com.javir.converter.dto.CurrencyDTO;

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
        holder.title.setText(data.get(position).getCurName());
        holder.rate.setText(data.get(position).getCurOfficialRate().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CurrencyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView rate;

        public CurrencyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.currencyCardView);
            title = (TextView) itemView.findViewById(R.id.currencyCardViewTitle);
            rate = (TextView) itemView.findViewById(R.id.currencyCardViewRate);
        }
    }
}

package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-02-08.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] dataSet;


    public RecyclerAdapter(String[] dataSet){
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
// - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.cardView.setCardBackgroundColor(position);
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }
}

package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.datx02_18_35.model.game.Level;

import java.util.ArrayList;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-03-18.
 */

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder>  {
    private ArrayList<Level> levels;
    Levels activity;


    LevelsAdapter(ArrayList<Level> levels, Levels activity){
        this.levels=levels;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_level, parent,false);
        return new LevelsAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

}

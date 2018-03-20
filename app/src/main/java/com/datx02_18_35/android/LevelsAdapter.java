package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by raxxor on 2018-03-18.
 */

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder>  {
    private ArrayList levels;
    Levels activity;


    LevelsAdapter(ArrayList levels, Levels activity){
        this.levels=levels;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
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

package com.datx02_18_35.android;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.lib.logicmodel.expression.Rule;

import java.util.ArrayList;
import java.util.Collections;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-03-01.
 */

public class RuleAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnClickListener {
    ArrayList<Rule> dataSet;


    public RuleAdapter(ArrayList<Rule> dataSet){
        this.dataSet=dataSet;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expression, parent,false);
        return new RecyclerAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onItemMove(int indexFrom, int indexTo) {
        Collections.swap(dataSet,indexFrom,indexTo);
        notifyItemMoved(indexFrom,indexTo);
        //implement
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}

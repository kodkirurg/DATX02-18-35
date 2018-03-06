package com.datx02_18_35.android;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.datx02_18_35.model.expression.Rule;

import java.util.ArrayList;
import java.util.Collections;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-03-01.
 */

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnClickListener {
    ArrayList<Rule> dataSet;


    public RuleAdapter(ArrayList<Rule> dataSet){
        this.dataSet=dataSet;
    }

    @Override
    public RuleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout frame = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rule, parent,false);
        return new RuleAdapter.ViewHolder(frame);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.frame.setBackgroundColor(Color.WHITE);
        ImageView imageView = holder.frame.findViewById(R.id.rule_imageview);
        switch (dataSet.get(position).type) {
            case CONJUNCTION_INTRODUCTION:
                imageView.setBackgroundResource(R.drawable.conjunction_introduction);
                break;
            case ABSURDITY_ELIMINATION:
                imageView.setBackgroundResource(R.drawable.absurdity_elimination);
                break;
            case CONJUNCTION_ELIMINATION:
                imageView.setBackgroundResource(R.drawable.conjunction_elimination);
                break;
            case IMPLICATION_INTRODUCTION:
                imageView.setBackgroundResource(R.drawable.implication_introduction);
                break;
            case DISJUNCTION_ELIMINATION:
                imageView.setBackgroundResource(R.drawable.disjunction_elimination);
                break;
            case DISJUNCTION_INTRODUCTION:
                imageView.setBackgroundResource(R.drawable.disjunction_introduction;
                break;
            case IMPLICATION_ELIMINATION:
                imageView.setBackgroundResource(R.drawable.implication_elimination);
                break;
                default:
                    Log.d("test123", "onBindViewHolder: Unknown rule, wtf?");
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,ItemTouchHelperViewHolder{
        public FrameLayout frame;


        public ViewHolder(FrameLayout itemView) {
            super(itemView);
            frame = itemView;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }

        @Override
        public void onClick(View view) {


        }
    }

}

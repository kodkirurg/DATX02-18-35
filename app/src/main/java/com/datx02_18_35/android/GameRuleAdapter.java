package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.RequestApplyRuleAction;
import com.datx02_18_35.model.expression.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-03-01.
 */

public class GameRuleAdapter extends RecyclerView.Adapter<GameRuleAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Rule> dataSet;


    GameRuleAdapter(ArrayList<Rule> dataSet){
        this.dataSet=dataSet;
    }


    void updateBoard(Collection<Rule> data){
        dataSet.clear();
        dataSet.addAll(data);
    }

    @Override
    public GameRuleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout frame = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rule, parent,false);
        return new GameRuleAdapter.ViewHolder(frame);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //set click listener
        holder.frame.setOnClickListener(this);
        holder.frame.setTag(position);

        //set visuals
        holder.frame.setBackgroundColor(Color.WHITE);
        ImageView imageView = holder.frame.findViewById(R.id.rule_imageview);
        if (dataSet.get(position) != null){
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
                    imageView.setBackgroundResource(R.drawable.disjunction_introduction);
                    break;
                case IMPLICATION_ELIMINATION:
                    imageView.setBackgroundResource(R.drawable.implication_elimination);
                    break;
                default:
                    Log.d("test123", "onBindViewHolder: Unknown rule, wtf?");
            }
        }

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onClick(View v) {
        try {
            Controller.getSingleton().sendAction(new RequestApplyRuleAction(GameBoard.boardCallback,dataSet.get((int)v.getTag())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FrameLayout frame;


        ViewHolder(FrameLayout itemView) {
            super(itemView);
            frame = itemView;
        }


        @Override
        public void onClick(View view) {


        }
    }

}

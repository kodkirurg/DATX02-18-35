package com.datx02_18_35.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.RequestRulesAction;
import com.datx02_18_35.model.expression.Absurdity;
import com.datx02_18_35.model.expression.Conjunction;
import com.datx02_18_35.model.expression.Disjunction;
import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.expression.Implication;
import com.datx02_18_35.model.expression.Operator;
import com.datx02_18_35.model.expression.Proposition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-02-08.
 */

public class GameCardAdapter extends RecyclerView.Adapter<GameCardAdapter.ViewHolder> implements View.OnClickListener {
    ArrayList<Expression> dataSet;
    ArrayList<Integer> selected=new ArrayList<>();
    HashMap<Integer, CardView> selectedView = new HashMap<>();
    private GameBoard activity;


    GameCardAdapter(ArrayList<Expression> dataSet, GameBoard activity){
        this.dataSet = dataSet;
        this.activity=activity;
    }

    void updateBoard(final Iterable<Expression> data){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataSet.clear();
                for (Expression expression : data) {dataSet.add(expression);}
                notifyDataSetChanged();
                selected.clear();
                selectedView.clear();
                try {
                    Controller.getSingleton().sendAction(new RequestRulesAction(GameBoard.boardCallback, new ArrayList<Expression>()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expression, parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //remember to check if selected and highlight on bind.
        holder.setIsRecyclable(false);
        holder.cardView.setOnClickListener(this);
        holder.cardView.setTag(position);
        if(null != dataSet.get(position) & !holder.alreadyBound){
            new Tools.CardDeflator(holder.cardView,dataSet.get(position));
            holder.alreadyBound=true;
        }
    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    @Override
    public void onClick(View v) {
        ((GameBoard)activity).newSelection(dataSet.get( (int) v.getTag()),(CardView) v);
    }

    void setSelection(Expression expression, CardView v) {
        selected.add((int) v.getTag());
        selectedView.put((int) v.getTag(),v);
        v.setBackgroundColor(Color.BLACK);
        v.setScaleX((float)1.05);
        v.setScaleY((float)1.05);
    }

    void resetSelection(Expression expression, CardView v) {
        for(int x =0 ; selected.size() > x ;x++){
           if (selected.get(x)==v.getTag()){
                selected.remove(x);
                break;
           }
        }

        CardView cardView = selectedView.get((int) v.getTag());
        cardView.setBackgroundColor(Color.WHITE);
        cardView.setScaleX((float)1.0);
        cardView.setScaleY((float)1.0);
        selectedView.remove(expression.hashCode());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        boolean alreadyBound=false;

        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

}


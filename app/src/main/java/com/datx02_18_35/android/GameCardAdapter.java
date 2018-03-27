package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.controller.Controller;
import com.datx02_18_35.controller.dispatch.actions.viewActions.RequestRulesAction;
import com.datx02_18_35.model.expression.Expression;

import java.util.ArrayList;
import java.util.HashMap;

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
                restoreSelections();
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
        holder.cardView.setOnClickListener(this);
        holder.cardView.setTag(position);
        holder.setIsRecyclable(false);
        if(selected.contains(position)){
            setAnimations(holder.cardView);
        }
        if(null != dataSet.get(position) & !holder.alreadyBound){
            CardDeflator.deflate(holder.cardView,dataSet.get(position),GameBoard.symbolMap);
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
        setAnimations(v);
    }

    void resetSelection(Expression expression, CardView v) {
        for(int x =0 ; selected.size() > x ;x++){
           if (selected.get(x)==v.getTag()){
                selected.remove(x);
                break;
           }
        }
        CardView cardView = selectedView.get((int) v.getTag());
        restoreAnimations(cardView);
        selectedView.remove(expression.hashCode());
    }

    void restoreSelections(){
        try {
            activity.gameChange.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(CardView view : selectedView.values()){
            restoreAnimations(view);
        }
        selected.clear();
        selectedView.clear();
        try {
            if(!GameBoard.victory){
                Controller.getSingleton().sendAction(new RequestRulesAction(GameBoard.boardCallback, new ArrayList<Expression>()));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.gameChange.release();
    }
    void restoreAnimations(CardView cardView){
        cardView.setBackgroundColor(Color.WHITE);
        Tools.deselectAnimation(cardView.getContext(), cardView);
    }
    void setAnimations(CardView cardView){
        cardView.setBackgroundColor(Color.BLACK);
        Tools.selectAnimation(cardView.getContext(), cardView);
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


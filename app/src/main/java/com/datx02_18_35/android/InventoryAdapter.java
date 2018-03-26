package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.datx02_18_35.model.expression.Expression;
import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.LevelProgression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import game.logic_game.R;

/**
 * Created by Magnu on 2018-03-21.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Expression> dataSet;
    private ArrayList<Expression> newSet;
    GameBoard activity;


    public InventoryAdapter(ArrayList<Expression> dataSet, GameBoard activity){
        this.dataSet = dataSet;
        this.activity = activity;
    }


    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expression, parent,false);
        return new InventoryAdapter.ViewHolder(cardView);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBindViewHolder(InventoryAdapter.ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(this);
        holder.cardView.setTag(position);
        holder.cardView.setTag(R.string.viewholders,holder);
        holder.cardView.setBackgroundColor(Color.WHITE);
        if(dataSet.get(position)!= null & !holder.alreadyBound) {
            new Tools.CardDeflator(holder.cardView, dataSet.get(position),GameBoard.symbolMap);
            holder.alreadyBound = true;
        }
    }
    public void updateInventory() {
        Iterator<Iterable<Expression>> invIterator = GameBoard.inventories.iterator();

        while (invIterator.hasNext()) {
            Iterable<Expression> inventory = invIterator.next();
            Iterator<Expression> expressionIterator = inventory.iterator();
            while (expressionIterator.hasNext()) {
                newSet.add(expressionIterator.next());
            }
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataSet = newSet;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        boolean alreadyBound=false;


        ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }



        @Override
        public void onClick(View view) {


        }
    }
}

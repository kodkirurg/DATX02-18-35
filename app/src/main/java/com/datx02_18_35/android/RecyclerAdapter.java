package com.datx02_18_35.android;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.datx02_18_35.lib.logicmodel.expression.Expression;

import java.util.ArrayList;
import java.util.Collections;

import game.logic_game.R;

/**
 * Created by raxxor on 2018-02-08.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter, View.OnClickListener {
    public ArrayList<Expression> dataSet;


    public RecyclerAdapter(){
        //empty const
    }

    public RecyclerAdapter(ArrayList<Expression> dataSet){
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

        holder.cardView.setCardBackgroundColor(Color.BLUE);
        holder.infoButton.setOnClickListener(this);
        holder.infoButton.setTag(position);
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public boolean onItemMove(int indexFrom, int indexTo) {
        Collections.swap(dataSet,indexFrom,indexTo);
        notifyItemMoved(indexFrom,indexTo);
        return false;
    }

    public void onItemDismiss(int adapterPosition) {
        //implement
    }

    @Override
    public void onClick(View v) {
        Log.d("test123","test ");
        int test = (int) v.getTag();
        Log.d("test123","" + test);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,ItemTouchHelperViewHolder{
        public CardView cardView;
        public ImageView infoButton;

        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
            infoButton = (ImageView) itemView.findViewById(R.id.info_button_on_card);
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
            cardView.setBackgroundColor(Color.BLACK);
        }
    }
}
